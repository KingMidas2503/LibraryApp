package dao;


import models.Book;
import models.Library;
import models.Rent;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.io.Reader;


public class LibrarySessionFactory {

    private static LibrarySessionFactory instance;

    private SessionFactory sessionFactory;

    private LibrarySessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            try {
                instance = new LibrarySessionFactory();
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Book.class);
                configuration.addAnnotatedClass(Library.class);
                configuration.addAnnotatedClass(Reader.class);
                configuration.addAnnotatedClass(Rent.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                instance.sessionFactory = configuration.buildSessionFactory(builder.build());
                System.out.println("SessionFactory created");
            } catch (Exception e) {
                System.out.println("Происходит какая-то фигня: " + e.getMessage());
                throw new ExceptionInInitializerError(e);
            }
        }
        return instance.sessionFactory;
    }
}
