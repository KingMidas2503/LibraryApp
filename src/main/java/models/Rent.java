package models;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
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


}
