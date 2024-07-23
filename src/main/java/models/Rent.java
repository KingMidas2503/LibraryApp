package models;

import dao.RentDAO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
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

    private RentDAO dao = new RentDAO();

    public Rent(Library library, Book book, Reader reader) {
        this.libraryId = library.getId();
        this.bookId = book.getId();
        this.readerId = reader.getId();
        this.isActive = false;
    }

    public void start() {
        dao.start(this);

    }

    public void stop() {
        dao.stop(this);
    }

}
