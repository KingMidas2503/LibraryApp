package models;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "reader")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String name;
    @Column
    boolean hasBeenToTheLibrary;

    public Reader(String name) {
        this.name = name;
        hasBeenToTheLibrary = false;
    }

    public Reader() {}

}
