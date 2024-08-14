package ru.intabia.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.intabia.models.Book;
import ru.intabia.models.Library;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class BookDAO {

    private final EntityManager entityManager;
    private final RentDAO rentDAO;

    @Transactional
    public void saveNewBook(Book book, long libraryId) {
        Session session = entityManager.unwrap(Session.class);
        book.setLibraryId(libraryId);
        session.persist(book);
    }

    @Transactional
    public List<Book> showAllBooks(long libraryId) {
        Session session = entityManager.unwrap(Session.class);
        List<Long> ids = session.createQuery("select b.id from Book b").list();
        List<Book> books = new ArrayList<>();
        for (long id : ids) {
            Book book = session.get(Book.class, id);
            session.refresh(book);
            if (book.getLibraryId() == libraryId && !book.isUsingNow()) {
                books.add(book);
            }
        }
        return books;
    }

    @Transactional
    public Book showABook(long libraryId, long bookId, boolean isLibrarian) {
        Session session = entityManager.unwrap(Session.class);
        Library library = session.get(Library.class, libraryId);
        Book book = session.get(Book.class, bookId);
        session.refresh(book);
        if (book != null && book.getLibraryId() == libraryId) {
            if (!book.isUsingNow() || isLibrarian) {
                return book;
            } else {
                log.info("Ошибка: книга \"{}\" находится в аренде. Ее нельзя посмотреть, не имея прав библиотекаря.", book.getTitle());
                return null;
            }
        } else {
            log.info("Ошибка: книга с указанным id не найдена в библиотеке \"{}\"", library.getTitle());
            return null;
        }
    }

    @Transactional
    public void acceptTheBook(long libraryId, long bookId, long readerId) {
        Session session = entityManager.unwrap(Session.class);
        Library library = session.get(Library.class, libraryId);
        Book book = session.get(Book.class, bookId);
        session.refresh(book);
        if (book != null && book.getLibraryId() == libraryId) {
            if (book.isUsingNow()) {
                book.setIsUsingNow(false);
                session.persist(book);
                rentDAO.stopRent(readerId, bookId, libraryId);
                log.info("Книга \"{}\" благополучно возвращена в библиотеку!", book.getTitle());
            } else {
                log.info("Ошибка: у читателя с указанным id нет книги \"{}\" в пользовании. Поэтому он не может ее вернуть", book.getTitle());
            }
        } else {
            log.info("Ошибка: книги с указанным id не было библиотеке \"{}\". Поэтому ее нельзя вернуть", library.getTitle());
        }
    }

    @Transactional
    public Book giveABook(long libraryId, long bookId, long readerId) {
        Session session = entityManager.unwrap(Session.class);
        Library library = session.get(Library.class, libraryId);
        Book book = session.get(Book.class, bookId);
        session.refresh(book);
        if (book != null && book.getLibraryId() == libraryId) {
            if (!book.isUsingNow()) {
                book.setIsUsingNow(true);
                session.persist(book);
                rentDAO.startRent(readerId, bookId, libraryId);
                return book;
            } else {
                log.error("Ошибка: книга \"{}\" находится в аренде. Подождите, пока другой читатель вернет ее в библиотеку.", book.getTitle());
                return null;
            }
        } else {
            log.error("Ошибка: книга с указанным id не найдена в библиотеке \"{}\"", library.getTitle());
            return null;
        }
    }

}
