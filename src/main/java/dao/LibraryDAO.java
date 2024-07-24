package dao;

import models.Book;
import models.Library;
import models.Rent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import service.BookService;
import service.LibraryService;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LibraryDAO {

    private Session librarySession;
    private RentDAO rentDAO = new RentDAO();

    public LibraryDAO() {
        librarySession = LibrarySessionFactory.getSessionFactory().openSession();
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
            if (book != null && book.getLibraryId() == libraryId && !book.isUsingNow()) {
                transaction.commit();
                Rent rent = new Rent(readerId, bookId, libraryId);
                rentDAO.startRent(rent);
                return book;
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
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            Library library = librarySession.get(Library.class, libraryId);
            Book book = librarySession.get(Book.class, bookId);
            if (book != null && book.getLibraryId() == libraryId) {
                if (book.isUsingNow()) {
                    transaction.commit();
                    Rent rent = new Rent(readerId, bookId, libraryId);
                    rentDAO.stopRent(rent);
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
/*
    public List<Book> showAllBooks(long libraryId, boolean isLibrarian) {
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            Library library = librarySession.get(Library.class, libraryId);
            List<Book> books = librarySession.createQuery("from books").list();
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
 */


    public int returnBookToLibrary(LibraryService libraryService, BookService bookService) {
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            Book selectedBook = librarySession.get(Book.class, bookService.getId());
            if (selectedBook != null && selectedBook.getLibrary().getId() == libraryService.getId()) {
                if (selectedBook.isUsingNow()) {
                    selectedBook.setUsingNow(false);
                    transaction.commit();
                    return 1;
                }
                else {
                    System.out.println("Логическая ошибка: книга \"" + bookService.getTitle() + "\" уже находится в библиотеке " + libraryService.getTitle() + ". Поэтому ее нельзя вернуть.");
                    return 0;
                }
            }
            else {
                System.out.println("Логическая ошибка: книга \"" + bookService.getTitle() + "\" никогда не находилась в библиотеке " + libraryService.getTitle() + ". Поэтому ее нельзя вернуть.");
                return 0;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
            return 0;
        }
    }
}
