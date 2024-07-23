package dao;

import models.Book;
import models.Library;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class LibraryDAO {

    private Session librarySession;


    public LibraryDAO() {
        librarySession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void createBookTable(Library library) {
        String title = library.getTitle().replaceAll(" ", "_").toUpperCase();
        String checkSqlString = "DROP TABLE IF EXISTS BOOKS_OF_" + title;
        String sqlString = "CREATE TABLE BOOKS_OF_"
                + title + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL)";
        librarySession.createQuery(checkSqlString).executeUpdate();
        librarySession.createQuery(sqlString).executeUpdate();
        System.out.println("Создана таблица с книгами библиотеки \"" + library.getTitle() + "\"");
    }



    public void addBookInLibrary(Library library, Book book) {
        Transaction transaction = null;
        try {
            book.setLibrary(library);
            book.setLibraryId(library.getId());
            book.setUsingNow(false);
            transaction = librarySession.beginTransaction();
            librarySession.save(book);
            librarySession.save(library);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public int takeBookFromLibrary(Library library, Book book) {
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            Book selectedBook = librarySession.get(Book.class, book.getId());
            if (selectedBook != null && selectedBook.getLibrary().getId() == library.getId()) {
                if (!selectedBook.isUsingNow()) {
                    selectedBook.setUsingNow(true);
                    transaction.commit();
                    return 1;
                }
                else {
                    System.out.println("Сейчас в библиотеке " + library.getTitle() + " нет книги \"" + book.getTitle() + "\". Подождите, когда ее вернут.");
                    return 0;
                }
            }
            else {
                System.out.println("В библиотеке " + library.getTitle() + " нет книги \"" + book.getTitle() + "\". Поэтому взять ее не получится." );
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
    public int returnBookToLibrary(Library library, Book book) {
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            Book selectedBook = librarySession.get(Book.class, book.getId());
            if (selectedBook != null && selectedBook.getLibrary().getId() == library.getId()) {
                if (selectedBook.isUsingNow()) {
                    selectedBook.setUsingNow(false);
                    transaction.commit();
                    return 1;
                }
                else {
                    System.out.println("Логическая ошибка: книга \"" + book.getTitle() + "\" уже находится в библиотеке " + library.getTitle() + ". Поэтому ее нельзя вернуть.");
                    return 0;
                }
            }
            else {
                System.out.println("Логическая ошибка: книга \"" + book.getTitle() + "\" никогда не находилась в библиотеке " + library.getTitle() + ". Поэтому ее нельзя вернуть.");
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
