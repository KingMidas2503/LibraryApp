package dto;

import lombok.Getter;


@Getter
public class LibraryDTO {

    private long id;
    private String title;

    public LibraryDTO(long id, String title) {
        this.id = id;
        this.title = title;
    }
}
