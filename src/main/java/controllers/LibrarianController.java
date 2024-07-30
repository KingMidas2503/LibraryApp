package controllers;

import dto.BookDTO;
import dto.LibraryDTO;
import dto.ReaderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.LibrarianService;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class LibrarianController implements LibraryUserController {

    private final LibrarianService librarianService;

    @RequestMapping("/libraryProject/api/v1/addBook")
    public void addBook(long libraryId, BookDTO bookDTO) {
        librarianService.addBook(libraryId, bookDTO);
    }


    @RequestMapping("/libraryProject/api/v1/lookAtBook")
    @Override
    public BookDTO lookAtBook(long libraryId, long bookId) {
        return librarianService.lookAtBook(libraryId, bookId);
    }

    @RequestMapping("/libraryProject/api/v1/lookAtAllBooks")
    @Override
    public List<BookDTO> lookAtAllBooks(long libraryId) {
        return librarianService.lookAtAllBooks(libraryId);
    }

    @RequestMapping("/libraryProject/api/v1/getReaderById")
    public ReaderDTO getReaderById(long readerId) {
        return librarianService.getReaderById(readerId);
    }

    @RequestMapping("/libraryProject/api/v1/getReaderById")
    @Override
    public LibraryDTO getLibraryById(long libraryId) {
        return librarianService.getLibraryById(libraryId);
    }
}
