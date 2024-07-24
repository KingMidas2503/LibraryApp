package controllers;

import dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.LibrarianService;


@Controller
public class LibrarianController implements LibraryUserController {

    @Autowired
    LibrarianService librarianService;

    @RequestMapping("/libraryProject/api/v1/takeABook")
    public void addABook(long libraryId, BookDTO bookDTO) {
        librarianService.addBook(libraryId, bookDTO);
    }


    @RequestMapping("/libraryProject/api/v1/lookAtBook")
    @Override
    public BookDTO lookAtBook(long libraryId, long bookId) {
        return librarianService.lookAtBook(libraryId, bookId);
    }

    /*@RequestMapping("/libraryProject/api/v1/lookAtAllBook")
    @Override
    public ArrayList<BookDTO> lookAtAllBooks(long libraryId) {
        return librarianService.lookAtAllBooks(libraryId);
    }*/
}
