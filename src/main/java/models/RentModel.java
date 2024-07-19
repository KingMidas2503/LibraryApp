package models;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class RentModel {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "LIBRARY_ID", referencedColumnName = "ID")
    private LibraryModel libraryModel;

    @OneToMany(mappedBy = "BOOK_MODEL")
    private List<BookModel> booksInRent;



    @PostPersist
    public void createRentTable() {
        String libraryTitle = libraryModel.getTitle().replaceAll(" ", "_").toUpperCase();
        String tableName = "RENT_OF_" + libraryTitle;
        String sqlString = "CREATE TABLE " + tableName
                + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL, READER_NAME VARCHAR(40) NOT NULL, IS_ACTIVE BOOLEAN NOT NULL)";
        EntityManager entityManager = Persistence.createEntityManagerFactory("library").createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sqlString).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
