package ru.intabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.intabia.dto.BookDTO;
import ru.intabia.dto.LibraryDTO;
import ru.intabia.dto.ReaderDTO;
import ru.intabia.service.LibrarianService;
import java.util.List;


@RestController
public class LibrarianController implements LibraryUserController {

    private final LibrarianService librarianService;

    @Autowired
    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @PostMapping(value = "/books")
    public ResponseEntity<?> addBook(@PathVariable(name="libraryId") long libraryId, @RequestBody BookDTO bookDTO) {
        librarianService.addBook(libraryId, bookDTO);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/books")
    @Override
    public ResponseEntity<BookDTO> lookAtBook(@PathVariable(name="libraryId") long libraryId, @PathVariable(name="bookId") long bookId) {
        BookDTO bookDTO = librarianService.lookAtBook(libraryId, bookId);
        return bookDTO != null ? new ResponseEntity<>(bookDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/books")
    @Override
    public ResponseEntity<List<BookDTO>> lookAtAllBooks(@PathVariable(name="libraryId") long libraryId) {
        List<BookDTO> allBooks = librarianService.lookAtAllBooks(libraryId);
        return allBooks != null && !allBooks.isEmpty() ? new ResponseEntity<>(allBooks, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/readers")
    public ResponseEntity<ReaderDTO> getReaderById(@PathVariable(name="readerId") long readerId) {
        ReaderDTO readerDTO = librarianService.getReaderById(readerId);
        return readerDTO != null ? new ResponseEntity<>(readerDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/library")
    @Override
    public ResponseEntity<LibraryDTO> getLibraryById(@PathVariable(name="libraryId") long libraryId) {
        LibraryDTO libraryDTO = librarianService.getLibraryById(libraryId);
        return libraryDTO != null ? new ResponseEntity<>(libraryDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
