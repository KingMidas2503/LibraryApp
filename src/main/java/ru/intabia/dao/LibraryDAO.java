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


    /*private final Session librarySession;

    public LibraryDAO() {
        librarySession = LibrarySessionFactory.getSessionFactory().openSession();
    }

    public void saveNewLibrary(Library library) {
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            librarySession.save(library);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public Library getLibraryById(long libraryId) {
        Library library = null;
        Transaction transaction = null;
        try {
            transaction = librarySession.beginTransaction();
            library = librarySession.get(Library.class, libraryId);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return library;
    }

}*/

