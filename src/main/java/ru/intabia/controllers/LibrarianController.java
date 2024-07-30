package ru.intabia.controllers;

import ru.intabia.dto.BookDTO;
import ru.intabia.dto.LibraryDTO;
import ru.intabia.dto.ReaderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.intabia.service.LibrarianService;
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

    @RequestMapping("/libraryProject/api/v1/getLibraryById")
    @Override
    public LibraryDTO getLibraryById(long libraryId) {
        return librarianService.getLibraryById(libraryId);
    }
}
