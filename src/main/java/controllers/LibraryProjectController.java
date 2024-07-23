package controllers;

import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LibraryProjectController {

    @Autowired
    private Library library;
    @Autowired
    private Reader reader;


    @RequestMapping("/libraryProject/api/v1/addBook")
    public void addBookInLibrary(Book book) {
        library.addBookInLibrary(book);
    }

    @RequestMapping("/libraryProject/api/v1/takeABook")
    public void takeABook(Library library, Book book) {
        reader.takeABook(library, book);
    }

    @RequestMapping("/libraryProject/api/v1/returnTheBook")
    public void returnTheBook(Library library, Book book) {
        reader.returnTheBook(library, book);
    }

}
