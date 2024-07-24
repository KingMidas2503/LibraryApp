package models;

import java.util.ArrayList;
import java.util.List;

import dao.LibraryDAO;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "library")
    private List<Book> books = new ArrayList<>();
    @Column
    private String title;

    public Library(String title) {
        this.title = title;
    }

}