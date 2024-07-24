package service;

import dao.LibraryDAO;
import dto.BookDTO;
import lombok.Getter;
import models.Book;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
public class LibraryService {

    @Autowired
    private LibraryDAO libraryDAO = new LibraryDAO();
    Map<String, ReaderService> readersRegistry = new HashMap<>();
    List<LibrarianService> workers = new ArrayList<>();

    private long id;
    private List<BookService> books = new ArrayList<>();
    private String title;


    public LibraryService(String title) {
        this.title = title;
    }

    public void addNewBook(Book book, long libraryId) {
        libraryDAO.saveNewBook(book, libraryId);
    }



}
