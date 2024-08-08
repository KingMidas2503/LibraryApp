package ru.intabia.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import ru.intabia.models.Reader;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class ReaderDAO {

    private final EntityManager entityManager;

    public ReaderDAO(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void saveNewReader(Reader reader) {
        try (Session session = entityManager.unwrap(Session.class)) {
            session.persist(reader);
        }
    }


    public Reader getReaderById(long readerId) {
        try (Session session = entityManager.unwrap(Session.class)) {
            return session.get(Reader.class, readerId);
        }
    }


}
