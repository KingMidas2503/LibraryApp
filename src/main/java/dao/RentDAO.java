package dao;

import models.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class RentDAO {

    private Session rentSession;


    public RentDAO() {
        rentSession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void start(Rent rent) {
        Transaction transaction = null;
        try {
            rent.setActive(true);
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
    public void stop(Rent rent) {
        Transaction transaction = null;
        try {
            transaction = rentSession.beginTransaction();
            Rent newRent = rentSession.get(Rent.class, rent.getId());
            if (newRent != null) {
                newRent.setActive(false);
                rentSession.update(newRent);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
