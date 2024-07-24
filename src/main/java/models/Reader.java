package models;

import java.util.Random;
import jakarta.persistence.*;


@Entity
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String name;


    public Reader(String name) {
        this.name = name;
    }

}
