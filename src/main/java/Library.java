import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Library {

    private String title;

    private int bookId;

    private boolean isEmpty;

    HashMap<Integer, Book> totalBooks;
    HashMap<Integer, Book> availableBooks;

    ArrayList<Librarian> workers;

    int countOfUsingBooks;

    HashMap<String, Reader> readersRegistry;

    public Library(String title) {
        this.title = title;
        totalBooks = new HashMap<>();
        availableBooks = new HashMap<>();
        workers = new ArrayList<>();
        readersRegistry = new HashMap<>();
        isEmpty = true;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void addBook(Book book) {
        totalBooks.put(bookId, book);
        availableBooks.put(bookId, book);
        bookId++;
        isEmpty = false;
    }
}


