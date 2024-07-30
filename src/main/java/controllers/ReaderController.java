package controllers;

import dto.BookDTO;
import dto.LibraryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ReaderService;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ReaderController implements LibraryUserController {

    private final ReaderService readerService;

    @RequestMapping("/libraryProject/api/v1/takeABook")
    public BookDTO takeABook(long libraryId, long bookId) {
        return readerService.takeABook(libraryId, bookId);
    }

    @RequestMapping("/libraryProject/api/v1/takeABook")
    public void returnTheBook(long libraryId, long bookId) {
        readerService.returnTheBook(libraryId, bookId);
    }

    @RequestMapping("/libraryProject/api/v1/lookAtBook")
    @Override
    public BookDTO lookAtBook(long libraryId, long bookId) {
        return readerService.lookAtBook(libraryId, bookId);
    }

    @RequestMapping("/libraryProject/api/v1/lookAtAllBook")
    @Override
    public List<BookDTO> lookAtAllBooks(long libraryId) {
        return readerService.lookAtAllBooks(libraryId);
    }

    @RequestMapping("/libraryProject/api/v1/getReaderById")
    @Override
    public LibraryDTO getLibraryById(long libraryId) {
        return readerService.getLibraryById(libraryId);
    }
}
