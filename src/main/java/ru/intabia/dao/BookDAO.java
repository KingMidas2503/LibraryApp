package ru.intabia.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.intabia.models.Book;
import ru.intabia.models.Library;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Repository
public class BookDAO {

    private final EntityManager entityManager;
    private final RentDAO rentDAO;

    public BookDAO(EntityManagerFactory entityManagerFactory, RentDAO rentDAO) {
        this.entityManager = entityManagerFactory.createEntityManager();
        this.rentDAO = rentDAO;
    }

    public void saveNewBook(Book book, long libraryId) {
        try (Session session = entityManager.unwrap(Session.class))  {
            book.setLibraryId(libraryId);
            session.persist(book);
        }
    }


    public List<Book> showAllBooks(long libraryId) {
        Transaction transaction = null;
        try (Session session = entityManager.unwrap(Session.class)) {
            transaction = session.beginTransaction();
            List<Long> ids = session.createQuery("select id from books").list();
            List<Book> books = new ArrayList<>();
            for (long id : ids) {
                Book book = session.get(Book.class, id);
                session.refresh(book);
                if (book.getLibraryId() == libraryId && !book.isUsingNow()) {
                    books.add(book);
                }
            }
            return books;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.error(e.getMessage(), e);
            }
            return null;
        }
    }


    public Book showABook(long libraryId, long bookId, boolean isLibrarian) {
        Transaction transaction = null;
        try (Session session = entityManager.unwrap(Session.class)) {
            transaction = session.beginTransaction();
            Library library = session.get(Library.class, libraryId);
            Book book = session.get(Book.class, bookId);
            session.refresh(book);
            if (book != null && book.getLibraryId() == libraryId) {
                if (!book.isUsingNow() || isLibrarian) {
                    transaction.commit();
                    return book;
                } else {
                    log.info("Ошибка: книга \"{}\" находится в аренде. Ее нельзя посмотреть, не имея прав библиотекаря.", book.getTitle());
                    return null;
                }
            } else {
                log.info("Ошибка: книга с указанным id не найдена в библиотеке \"{}\"", library.getTitle());
                transaction.commit();
                return null;
            }

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.error(e.getMessage(), e);
            }
            return null;
        }
    }


    public void acceptTheBook(long libraryId, long bookId, long readerId) {
        Transaction transaction = null;
        try (Session session = entityManager.unwrap(Session.class)) {
            transaction = session.beginTransaction();
            Library library = session.get(Library.class, libraryId);
            Book book = session.get(Book.class, bookId);
            session.refresh(book);
            if (book != null && book.getLibraryId() == libraryId) {
                if (book.isUsingNow()) {
                    book.setIsUsingNow(false);
                    session.persist(book);
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
                log.error(e.getMessage(), e);
            }
        }
    }


    public Book giveABook(long libraryId, long bookId, long readerId) {
        Transaction transaction = null;
        try (Session session = entityManager.unwrap(Session.class)) {
            transaction = session.beginTransaction();
            Library library = session.get(Library.class, libraryId);
            Book book = session.get(Book.class, bookId);
            session.refresh(book);
            if (book != null && book.getLibraryId() == libraryId) {
                if (!book.isUsingNow()) {
                    book.setIsUsingNow(true);
                    session.persist(book);
                    transaction.commit();
                    rentDAO.startRent(readerId, bookId, libraryId);
                    return book;

                } else {
                    log.error("Ошибка: книга \"{}\" находится в аренде. Подождите, пока другой читатель вернет ее в библиотеку.", book.getTitle());
                    transaction.rollback();
                    return null;
                }
            } else {
                log.error("Ошибка: книга с указанным id не найдена в библиотеке \"{}\"", library.getTitle());
                transaction.commit();
                return null;
            }

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.error(e.getMessage(), e);
            }
            return null;
        }
    }

}
