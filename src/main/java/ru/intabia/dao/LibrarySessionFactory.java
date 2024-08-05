package ru.intabia.dao;

import lombok.extern.slf4j.Slf4j;
import ru.intabia.models.Book;
import ru.intabia.models.Library;
import ru.intabia.models.Rent;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.io.Reader;

@Slf4j
public class LibrarySessionFactory {

    private static LibrarySessionFactory instance = new LibrarySessionFactory();

    private SessionFactory sessionFactory;

    private LibrarySessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (instance.sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Book.class);
                configuration.addAnnotatedClass(Library.class);
                configuration.addAnnotatedClass(Reader.class);
                configuration.addAnnotatedClass(Rent.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                instance.sessionFactory = configuration.buildSessionFactory(builder.build());
                log.info("SessionFactory created");
            } catch (Exception e) {
                log.info("Происходит какая-то фигня: {}", e.getMessage());
                throw new ExceptionInInitializerError(e);
            }
        }
        return instance.sessionFactory;
    }
}
