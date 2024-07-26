package dao;

import models.Librarian;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LibrarianDAO {

    private Session librarianSession;

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
