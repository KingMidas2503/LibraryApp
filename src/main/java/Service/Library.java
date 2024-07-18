package Service;

import dao.LibraryDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "LIBRARIES")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "LIBRARY_ID")
    private List<Book> books = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "LIBRARY_ID")
    List<Librarian> workers = new ArrayList<>();



    @Getter
    private String title;

    private int bookId;
    Rent rent;
    Map<String, Reader> readersRegistry = new HashMap<>();
    LibraryDAO libraryDAO = new LibraryDAO();

    public Library() {}


    public Library(String title) {
        this.title = title;
        libraryDAO.createBookTable(this);
        rent = new Rent(this);
    }

    public void addBook(Book book) {
        bookId++;
        LibraryDAO libraryDAO = new LibraryDAO();
        book.setId(bookId);
        libraryDAO.addBookInTable(this, book, bookId);
    }
}


