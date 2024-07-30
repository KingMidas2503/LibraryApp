package ru.intabia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BookDTO {

    private long id;
    private String title;
    private String author;

    @Override
    public String toString() {
        return "id: " + id + "; книга: \"" + title + "\"; автор: " + author;
    }
}
