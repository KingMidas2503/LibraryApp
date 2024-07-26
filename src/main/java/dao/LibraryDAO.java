package dao;

import dto.LibraryDTO;
import models.Book;
import models.Library;
import models.Reader;
import models.Rent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository
public class LibraryDAO {

    private Session librarySession;
    private RentDAO rentDAO = new RentDAO();

    public LibraryDAO() {
        librarySession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void saveNewLibrary(Library library) {
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            librarySession.save(library);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public void saveNewBook(Book book, long libraryId) {
        Transaction transaction = null;
        try {
            book.setLibraryId(libraryId);
            transaction = librarySession.beginTransaction();
            librarySession.save(book);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public Book giveABook(long libraryId, long bookId, long readerId) {
        Transaction transaction = librarySession.getTransaction();
        try {
            transaction.begin();
            Library library = librarySession.get(Library.class, libraryId);
            Book book = librarySession.get(Book.class, bookId);
            librarySession.refresh(book);
            if (book != null && book.getLibraryId() == libraryId) {
                if (!book.isUsingNow()) {
                    book.setUsingNow(true);
                    librarySession.update(book);
                    transaction.commit();
                    rentDAO.startRent(readerId, bookId, libraryId);
                    return book;

                }
                else {
                    System.out.println("Ошибка: книга \"" + book.getTitle() + "\" находится в аренде. Подождите, пока другой читатель вернет ее в библиотеку.");
                    transaction.rollback();
                    return null;
                }
            }
            else {
                System.out.println("Ошибка: книга с указанным id не найдена в библиотеке \"" + library.getTitle() + "\"");
                transaction.commit();
                return null;
            }

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
            return null;
        }
    }
    public void acceptTheBook(long libraryId, long bookId, long readerId) {
        Transaction transaction = librarySession.getTransaction();
        try {
            transaction.begin();
            Library library = librarySession.get(Library.class, libraryId);
            Book book = librarySession.get(Book.class, bookId);
            librarySession.refresh(book);
            if (book != null && book.getLibraryId() == libraryId) {
                if (book.isUsingNow()) {
                    book.setUsingNow(false);
                    librarySession.update(book);
                    transaction.commit();
                    rentDAO.stopRent(readerId, bookId, libraryId);
                    System.out.println("Книга \"" + book.getTitle() + "\" благополучно возвращена в библиотеку!");
                }
                else {
                    System.out.println("Ошибка: у читателя с указанным id нет книги \"" + book.getTitle() + "\" в пользовании. Поэтому он не может ее вернуть");
                    transaction.commit();
                }
            }
            else {
                System.out.println("Ошибка: книги с указанным id не было библиотеке \"" + library.getTitle() + "\". Поэтому ее нельзя вернуть");
            }

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public Book showABook(long libraryId, long bookId, boolean isLibrarian) {
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            Library library = librarySession.get(Library.class, libraryId);
            Book book = librarySession.get(Book.class, bookId);
            librarySession.refresh(book);
            if (book != null && book.getLibraryId() == libraryId) {
                if (!book.isUsingNow() || isLibrarian) {
                    transaction.commit();
                    return book;
                } else {
                    System.out.println("Ошибка: книга \"" + book.getTitle() + "\" находится в аренде. Ее нельзя посмотреть, не имея прав библиотекаря.");
                    return null;
                }
            }
            else {
                System.out.println("Ошибка: книга с указанным id не найдена в библиотеке \"" + library.getTitle() + "\"");
                transaction.commit();
                return null;
            }

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
            return null;
        }
    }

    public List<Book> showAllBooks(long libraryId, boolean isLibrarian) {
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            List<Long> ids = librarySession.createQuery("select id from Book").list();
            List<Book> books = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++) {
                long id = ids.get(i);
                Book book = librarySession.get(Book.class, id);
                librarySession.refresh(book);
                if (book.getLibraryId() == libraryId && (!book.isUsingNow() || isLibrarian)) {
                    books.add(book);
                }
            }
            return books;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
            return null;
        }
    }

    public List<Rent> showAllRents(long libraryId) {
        return rentDAO.showAllRents(libraryId);
    }

    public Reader getReaderById(long readerId) {
        Transaction transaction = librarySession.getTransaction();
        Reader reader = null;
        try {
            transaction.begin();
            reader = librarySession.get(Reader.class, readerId);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return reader;
    }

    public Library getLibraryById(long libraryId) {
        Transaction transaction = librarySession.getTransaction();
        Library library = null;
        try {
            transaction.begin();
            library = librarySession.get(Library.class, libraryId);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return library;
    }

}
