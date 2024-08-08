package ru.intabia.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "reader_id")
    private Long readerId;
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "library_id")
    private Long libraryId;
    @Column(name = "is_active")
    private Boolean isActive;

    public Rent(long readerId, long bookId, long libraryId) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.libraryId = libraryId;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public Rent() {}


}

