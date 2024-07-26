package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column
    private long readerId;
    @Column
    private long bookId;
    @Column
    private long libraryId;
    @Column
    @Setter
    private boolean isActive;

    public Rent(long readerId, long bookId, long libraryId) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.libraryId = libraryId;
        isActive = true;
    }

    public Rent() {}


}
