package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    @Setter
    private long libraryId;
    @Column
    @Setter
    private boolean isUsingNow;
    @Setter
    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

}
