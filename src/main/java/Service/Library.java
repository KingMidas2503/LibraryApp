package Service;

import dao.LibraryDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class Library {

    @Getter
    private String title;

    private int bookId;
    Rent rent;
    List<Librarian> workers = new ArrayList<>();;
    Map<String, Reader> readersRegistry = new HashMap<>();;
    LibraryDAO libraryDAO = new LibraryDAO();



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


