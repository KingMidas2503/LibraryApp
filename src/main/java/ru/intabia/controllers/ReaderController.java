package ru.intabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.intabia.dto.BookDTO;
import ru.intabia.dto.LibraryDTO;
import ru.intabia.dto.ReaderDTO;
import ru.intabia.service.ReaderService;
import java.util.List;


@RestController
@RequestMapping("reader")
public class ReaderController implements LibraryUserController {

    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping(value = "/takeABook/{id}")
    public ResponseEntity<BookDTO> takeABook(@PathVariable(name="libraryId") long libraryId, @PathVariable(name="bookId") long bookId) {
        BookDTO bookDTO = readerService.takeABook(libraryId, bookId);
        return bookDTO != null ? new ResponseEntity<>(bookDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/returnTheBook/{id}")
    public ResponseEntity<?> returnTheBook(@PathVariable(name="libraryId") long libraryId, @PathVariable(name="bookId") long bookId) {
        readerService.returnTheBook(libraryId, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/lookAtBook/{id}")
    @Override
    public ResponseEntity<BookDTO> lookAtBook(@PathVariable(name="libraryId") long libraryId, @PathVariable(name="bookId") long bookId) {
        BookDTO bookDTO = readerService.lookAtBook(libraryId, bookId);
        return bookDTO != null ? new ResponseEntity<>(bookDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/lookAtAllBooks/{id}")
    @Override
    public ResponseEntity<List<BookDTO>> lookAtAllBooks(@PathVariable(name="libraryId") long libraryId) {
        List<BookDTO> allBooks = readerService.lookAtAllBooks(libraryId);
        return allBooks != null && !allBooks.isEmpty() ? new ResponseEntity<>(allBooks, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getLibraryById/{id}")
    @Override
    public ResponseEntity<LibraryDTO> getLibraryById(@PathVariable(name="libraryId") long libraryId) {
        LibraryDTO libraryDTO = readerService.getLibraryById(libraryId);
        return libraryDTO != null ? new ResponseEntity<>(libraryDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/saveNewReader/{id}")
    public ResponseEntity<?> saveNewReader(@RequestBody ReaderDTO readerDTO) {
        readerService.saveNewReader(readerDTO);
        return ResponseEntity.ok().build();
    }
}
