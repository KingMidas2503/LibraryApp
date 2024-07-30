package ru.intabia.dao;

import ru.intabia.models.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class RentDAO {

    private Session rentSession;

    private Map<Long, Long> rentMap = new HashMap<>();

    public RentDAO() {
        rentSession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void startRent(long readerId, long bookId, long libraryId) {
        Transaction transaction = null;
        try {
            Rent rent = new Rent(readerId, bookId, libraryId);
            transaction = rentSession.beginTransaction();
            rentSession.save(rent);
            rentMap.put(bookId, rent.getId());
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
    public void stopRent(long readerId, long bookId, long libraryId) {
        Transaction transaction = null;
        try {
            long rentId = rentMap.get(bookId);
            rentMap.remove(bookId);
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
