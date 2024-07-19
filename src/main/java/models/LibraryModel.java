package models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
public class LibraryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    private String title;

    @OneToMany(mappedBy = "libraryModel")
    private List<BookModel> books;

    @OneToOne
    @JoinColumn(name = "RENT_ID", referencedColumnName = "ID")
    private RentModel rentModel;


    @PostPersist
    public void createBookTable() {
        String tableName = "BOOKS_OF_" + title.toUpperCase().replaceAll(" ", "_");
        String sqlString = "CREATE TABLE " + tableName + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL)";
        EntityManager entityManager = Persistence.createEntityManagerFactory("library").createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sqlString).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}