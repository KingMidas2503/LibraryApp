package models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ReaderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "readerModel")
    private List<BookModel> booksInUse;


    @PostPersist
    public void createBookTable() {
        String tableName = "BOOKS_IN_USE_OF_" + name.toUpperCase().replaceAll(" ", "_");
        String sqlString = "CREATE TABLE " + tableName + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL)";
        EntityManager entityManager = Persistence.createEntityManagerFactory("library").createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sqlString).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
