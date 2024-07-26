package dto;

import lombok.Getter;


@Getter
public class LibrarianDTO {
    private long id;
    private String name;

    public LibrarianDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
