package controllers;

import Service.Book;
import Service.Library;
import Service.Reader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class ReaderController {

    private Reader reader;

    public ReaderController() throws IOException {
        reader = new Reader(chooseReaderName());
    }

    private String chooseReaderName() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        return name;
    }

    @RequestMapping("/library/api/v1/take")
    public void takeABook(Library library, Book book) {
        reader.takeABook(library, book);
    }
    @RequestMapping("/library/api/v1/return")
    public void returnTheBook(Library library, Book book) {
        reader.returnTheBook(library, book);
    }

}
