package dto;

import lombok.Getter;

@Getter
public class BookDTO {

    private long id;
    private String title;
    private String author;

    public BookDTO(long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
