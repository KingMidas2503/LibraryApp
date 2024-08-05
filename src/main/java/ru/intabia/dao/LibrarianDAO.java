package ru.intabia.dao;

import ru.intabia.models.Librarian;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class LibrarianDAO {

    private final Session librarianSession;

    public LibrarianDAO() {
        librarianSession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void saveNewLibrarian(Librarian librarian) {
        Transaction transaction = null;
        try {
            transaction = librarianSession.beginTransaction();
            librarianSession.save(librarian);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
