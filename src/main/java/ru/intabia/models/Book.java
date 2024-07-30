package ru.intabia.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column(name = "library_id")
    private Long libraryId;
    @Column(name = "is_using_now")
    private Boolean isUsingNow;
    @ManyToOne
    @JoinColumn(name = "library_id", insertable = false, updatable = false)
    private Library library;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public boolean isUsingNow() {
        return isUsingNow;
    }

    public Book() {}

}
