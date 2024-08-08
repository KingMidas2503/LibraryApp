package ru.intabia.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import ru.intabia.models.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository
@Slf4j
public class RentDAO {

    private final EntityManager entityManager;

    public RentDAO(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void startRent(long readerId, long bookId, long libraryId) {
        try (Session session = entityManager.unwrap(Session.class)) {
            session.persist(new Rent(readerId, bookId, libraryId));
        }
    }

    public void stopRent(long readerId, long rentId, long libraryId) {
        Transaction transaction = null;
        try (Session session = entityManager.unwrap(Session.class)) {
            transaction = session.beginTransaction();
            Rent checkRent = session.get(Rent.class, rentId);
            session.refresh(checkRent);
            if (checkRent != null && checkRent.getReaderId() == readerId && checkRent.getLibraryId() == libraryId) {
                checkRent.setIsActive(false);
            }
            session.persist(checkRent);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.error(e.getMessage(), e);
            }
        }
    }

    public List<Rent> showAllRents(long libraryId) {
        Transaction transaction = null;
        List<Rent> rents = new ArrayList<>();
        try (Session session = entityManager.unwrap(Session.class)) {
            transaction = session.beginTransaction();
            List<Long> ids = session.createQuery("select id from Rent").list();
            for (int i = 0; i < ids.size(); i++) {
                long id = ids.get(i);
                Rent rent = session.get(Rent.class, id);
                session.refresh(rent);
                if (rent.getLibraryId() == libraryId) {
                    rents.add(rent);
                }
            }
            return rents;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.error(e.getMessage(), e);
            }
            return null;
        }
    }
}
