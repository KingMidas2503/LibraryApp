package ru.intabia.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "has_been_to_the_library")
    boolean hasBeenToTheLibrary;

    public Reader(String name) {
        this.name = name;
        hasBeenToTheLibrary = false;
    }

    public Reader() {}

}
