package ru.intabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.intabia.dto.BookDTO;
import ru.intabia.dto.RentDTO;
import ru.intabia.service.LibraryService;
import java.util.List;


@RestController
@RequestMapping("library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping(value = "/showRentTable/{id}")
    public ResponseEntity<List<RentDTO>> showRentTable(@PathVariable(name="libraryId") long libraryId) {
        List<RentDTO> rentDTOs = libraryService.showRentTable(libraryId);
        return rentDTOs != null && !rentDTOs.isEmpty() ? new ResponseEntity<>(rentDTOs, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value = "/addNewBook/{id}")
    public ResponseEntity<?> addNewBook(@RequestBody BookDTO bookDTO, @PathVariable(name="libraryId") long libraryId) {
        libraryService.addNewBook(bookDTO, libraryId);
        return ResponseEntity.ok().build();
    }
}
