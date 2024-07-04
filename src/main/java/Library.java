import java.util.ArrayList;
import java.util.HashMap;

public class Library {

    private String title;

    ArrayList<Book> totalBooks;
    ArrayList<Book> availableBooks;

    int countOfUsingBooks;

    HashMap<String, Reader> readersRegistry;

    public Library(String title) {
        this.title = title;
        totalBooks = new ArrayList<>();
        availableBooks = new ArrayList<>();
        readersRegistry = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public void addBook(Book book) {
        totalBooks.add(book);
        availableBooks.add(book);
    }

}


