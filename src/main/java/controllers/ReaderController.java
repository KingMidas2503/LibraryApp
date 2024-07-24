package controllers;

import dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ReaderService;


@Controller
public class ReaderController implements LibraryUserController {

    @Autowired
    ReaderService readerService;

    @RequestMapping("/libraryProject/api/v1/takeABook")
    public BookDTO takeABook(long libraryId, long bookId) {
        return readerService.takeABook(libraryId, bookId);
    }

    public void returnTheBook(long libraryId, BookDTO bookDTO) {
        readerService.returnTheBook(libraryId, bookDTO);
    }


    @RequestMapping("/libraryProject/api/v1/lookAtBook")
    @Override
    public BookDTO lookAtBook(long libraryId, long bookId) {
        return readerService.lookAtBook(libraryId, bookId);
    }

    /*@RequestMapping("/libraryProject/api/v1/lookAtAllBook")
    @Override
    public ArrayList<BookDTO> lookAtAllBooks(long libraryId) {
        return readerService.lookAtAllBooks(libraryId);
    }*/
}
