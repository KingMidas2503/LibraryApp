package ru.intabia.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Service
@Getter
public class BookService {

    private long id;
    private String title;
    private String author;
    @Setter
    private long libraryId;
    @Setter
    private boolean isUsingNow;
    @Setter
    private LibraryService libraryService;

    public BookService(String title, String author) {
        this.title = title;
        this.author = author;
    }

}
