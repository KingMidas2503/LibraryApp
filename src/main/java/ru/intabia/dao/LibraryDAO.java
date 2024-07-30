package ru.intabia.dao;

import lombok.extern.slf4j.Slf4j;
import ru.intabia.models.Book;
import ru.intabia.models.Library;
import ru.intabia.models.Reader;
import ru.intabia.models.Rent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            Library library = librarySession.get(Library.class, libraryId);
            Book book = librarySession.get(Book.class, bookId);
            librarySession.refresh(book);
            if (book != null && book.getLibraryId() == libraryId) {
                if (!book.isUsingNow()) {
                    book.setIsUsingNow(true);
                    librarySession.update(book);
                    transaction.commit();
                    rentDAO.startRent(readerId, bookId, libraryId);
                    return book;

                }
                else {
                    log.info("Ошибка: книга \"{}\" находится в аренде. Подождите, пока другой читатель вернет ее в библиотеку.", book.getTitle());
                    transaction.rollback();
                    return null;
                }
            }
            else {
                log.info("Ошибка: книга с указанным id не найдена в библиотеке \"{}\"", library.getTitle());
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
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            Library library = librarySession.get(Library.class, libraryId);
            Book book = librarySession.get(Book.class, bookId);
            librarySession.refresh(book);
            if (book != null && book.getLibraryId() == libraryId) {
                if (book.isUsingNow()) {
                    book.setIsUsingNow(false);
                    librarySession.update(book);
                    transaction.commit();
                    rentDAO.stopRent(readerId, bookId, libraryId);
                    log.info("Книга \"{}\" благополучно возвращена в библиотеку!", book.getTitle());
                }
                else {
                    log.info("Ошибка: у читателя с указанным id нет книги \"{}\" в пользовании. Поэтому он не может ее вернуть", book.getTitle());
                    transaction.commit();
                }
            }
            else {
                log.info("Ошибка: книги с указанным id не было библиотеке \"{}\". Поэтому ее нельзя вернуть", library.getTitle());
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
                    log.info("Ошибка: книга \"{}\" находится в аренде. Ее нельзя посмотреть, не имея прав библиотекаря.", book.getTitle());
                    return null;
                }
            }
            else {
                log.info("Ошибка: книга с указанным id не найдена в библиотеке \"{}\"", library.getTitle());
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
        Reader reader = null;
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
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
        Library library = null;
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
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
