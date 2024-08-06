package ru.intabia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.intabia.dto.BookDTO;
import ru.intabia.service.BookService;

import java.util.List;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/addBook/{libraryId}")
    public ResponseEntity<?> addBook(@PathVariable(name = "libraryId") long libraryId, @RequestBody BookDTO bookDTO) {
        bookService.addBook(libraryId, bookDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/lookAtBook/{bookId}")
    public ResponseEntity<BookDTO> lookAtBook(@RequestParam(name = "libraryId") long libraryId,
                                              @PathVariable(name = "bookId") long bookId) {
        BookDTO bookDTO = bookService.lookAtBook(libraryId, bookId);
        return bookDTO != null
                ? new ResponseEntity<>(bookDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/lookAtAllBooks/{libraryId}")
    public ResponseEntity<List<BookDTO>> lookAtAllBooks(@PathVariable(name = "libraryId") long libraryId) {
        List<BookDTO> allBooks = bookService.lookAtAllBooks(libraryId);
        return allBooks != null && !allBooks.isEmpty()
                ? new ResponseEntity<>(allBooks, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/takeABook/{bookId}")
    public ResponseEntity<BookDTO> takeABook(@RequestParam(name="libraryId") long libraryId,
                                             @RequestParam(name="readerId") long readerId,
                                             @PathVariable(name="bookId") long bookId) {

        BookDTO bookDTO = bookService.takeABook(libraryId, bookId, readerId);
        return bookDTO != null
                ? new ResponseEntity<>(bookDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/returnTheBook/{bookId}")
    public ResponseEntity<?> returnTheBook(@RequestParam(name="libraryId") long libraryId,
                                           @RequestParam(name="readerId") long readerId,
                                           @PathVariable(name="bookId") long bookId) {

        bookService.returnTheBook(libraryId, bookId, readerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
