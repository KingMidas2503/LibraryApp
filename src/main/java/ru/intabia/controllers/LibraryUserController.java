package ru.intabia.controllers;

import org.springframework.http.ResponseEntity;
import ru.intabia.dto.BookDTO;
import ru.intabia.dto.LibraryDTO;
import java.util.List;


public interface LibraryUserController {

   ResponseEntity<BookDTO> lookAtBook(long libraryId, long bookId);
   ResponseEntity<List<BookDTO>> lookAtAllBooks(long libraryId);
   ResponseEntity<LibraryDTO> getLibraryById(long libraryId);
}
