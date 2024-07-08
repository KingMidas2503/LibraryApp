package Service;

import dao.LibraryDAO;

import java.util.ArrayList;
import java.util.HashMap;

public class Library {

    private String title;

    private int bookId;

    Rent rent;

    ArrayList<Librarian> workers;

    HashMap<String, Reader> readersRegistry;


    public Library(String title) {
        this.title = title;
        workers = new ArrayList<>();
        readersRegistry = new HashMap<>();
        LibraryDAO libraryDAO = new LibraryDAO();
        libraryDAO.createBookTable(this);
        rent = new Rent(this);
    }

    public String getTitle() {
        return title;
    }


    public void addBook(Book book) {
        bookId++;
        LibraryDAO libraryDAO = new LibraryDAO();
        libraryDAO.addBookInTable(this, book, bookId);
    }
}


