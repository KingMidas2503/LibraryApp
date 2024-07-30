package ru.intabia.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "library")
    private List<Book> books = new ArrayList<>();
    @Column
    private String title;

    public Library(String title) {
        this.title = title;
    }

    public Library() {}
}