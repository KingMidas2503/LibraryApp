package ru.intabia.dao;

import ru.intabia.models.Reader;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class ReaderDAO {

    private Session readerSession;

    public ReaderDAO() {
        readerSession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void saveNewReader(Reader reader) {
        Transaction transaction = null;
        try {
            transaction = readerSession.beginTransaction();
            readerSession.save(reader);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
