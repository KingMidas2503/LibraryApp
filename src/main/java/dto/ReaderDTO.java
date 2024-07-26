package dto;

import lombok.Getter;


@Getter
public class ReaderDTO {
    private long id;
    private String name;

    public ReaderDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
