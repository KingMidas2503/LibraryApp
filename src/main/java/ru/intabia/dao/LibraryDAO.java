package ru.intabia.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import ru.intabia.models.Library;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
public class LibraryDAO {

    private final EntityManager entityManager;

    public LibraryDAO(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void saveNewLibrary(Library library) {
        try (Session session = entityManager.unwrap(Session.class)) {
            session.persist(library);
        }
    }

    public Library getLibraryById(long libraryId) {
        try (Session session = entityManager.unwrap(Session.class)) {
            return session.get(Library.class, libraryId);
        }
    }

}

