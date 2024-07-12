package Service;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Book {
    @Setter
    private int id;
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

}
