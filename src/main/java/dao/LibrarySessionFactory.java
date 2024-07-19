package dao;


import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

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
                instance.sessionFactory = configuration.buildSessionFactory();
                System.out.println("SessionFactory created");
            } catch (Exception e) {
                System.out.println("Происходит какая-то фигня: " + e.getMessage());
                throw new ExceptionInInitializerError(e);
            }
        }
        return instance.sessionFactory;
    }
}
