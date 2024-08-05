package ru.intabia.dao;

import ru.intabia.models.Rent;
import ru.intabia.models.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository
public class RentDAO {

    private final Session rentSession;

    public RentDAO() {
        rentSession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void startRent(long readerId, long bookId, long libraryId) {
        Transaction transaction = null;
        try {
            Rent rent = new Rent(readerId, bookId, libraryId);
            transaction = rentSession.beginTransaction();
            rentSession.save(rent);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public void stopRent(long readerId, long rentId, long libraryId) {
        Transaction transaction = null;
        try {
            transaction = rentSession.beginTransaction();
            Rent checkRent = rentSession.get(Rent.class, rentId);
            rentSession.refresh(checkRent);
            if (checkRent != null && checkRent.getReaderId() == readerId && checkRent.getLibraryId() == libraryId) {
                checkRent.setIsActive(false);
            }
            rentSession.update(checkRent);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public List<Rent> showAllRents(long libraryId) {
        Transaction transaction = null;
        List<Rent> rents = new ArrayList<>();
        try {
            transaction = rentSession.beginTransaction();
            List<Long> ids = rentSession.createQuery("select id from Rent").list();
            for (int i = 0; i < ids.size(); i++) {
                long id = ids.get(i);
                Rent rent = rentSession.get(Rent.class, id);
                rentSession.refresh(rent);
                if (rent.getLibraryId() == libraryId) {
                    rents.add(rent);
                }
            }
            return rents;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
            return null;
        }
    }
}
