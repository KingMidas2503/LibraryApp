package models;

import dao.LibraryDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "library")
public class Library {

    private LibraryDAO libraryDAO = new LibraryDAO();
    Map<String, Reader> readersRegistry = new HashMap<>();
    List<Librarian> workers = new ArrayList<>();

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

    public void addBookInLibrary(Book book) {
        books.add(book);
         libraryDAO.addBookInLibrary(this, book);
    }
    public int takeBookFromLibrary(Book book) {
        books.remove(book);
        return libraryDAO.takeBookFromLibrary(this, book);
    }
    public int returnBookToLibrary(Book book) {
        books.add(book);
        return libraryDAO.returnBookToLibrary(this, book);
    }


}


