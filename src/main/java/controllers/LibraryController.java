package controllers;

import Service.Book;
import Service.Library;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class LibraryController {

    private Library library;

    public LibraryController() throws IOException {
        library = new Library(chooseLibraryTitle());
    }

    private String chooseLibraryTitle() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String title = reader.readLine();
        return title;
    }

    @RequestMapping("/library/api/v1/create")
    public void addNewBook(Book book) {
        library.addBook(book);
    }
}
