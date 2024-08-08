package ru.intabia.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import ru.intabia.models.Librarian;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.intabia.models.Reader;


@Repository
public class LibrarianDAO {

    private final EntityManager entityManager;

    public LibrarianDAO(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void saveNewLibrarian(Librarian librarian) {
        try (Session session = entityManager.unwrap(Session.class)) {
            session.persist(librarian);
        }
    }

    public Librarian getLibrarianById(long librarianId) {
        try (Session session = entityManager.unwrap(Session.class)) {
            return session.get(Librarian.class, librarianId);
        }
    }

}
