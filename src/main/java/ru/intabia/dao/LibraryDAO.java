package ru.intabia.dao;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.intabia.models.Library;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LibraryDAO {

    private final Session librarySession;

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

}

