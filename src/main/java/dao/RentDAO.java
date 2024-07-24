package dao;

import models.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class RentDAO {

    private Session rentSession;


    public RentDAO() {
        rentSession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void startRent(Rent rent) {
        Transaction transaction = null;
        try {
            rent.setActive(true);
            transaction = rentSession.beginTransaction();
            rentSession.save(rent);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
    public void stopRent(Rent rent) {
        Transaction transaction = null;
        try {
            rent.setActive(false);
            transaction = rentSession.beginTransaction();
            rentSession.save(rent);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
