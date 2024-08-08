package ru.intabia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.intabia.dto.LibrarianDTO;
import ru.intabia.service.LibrarianService;


@RestController
@RequestMapping("reader")
@RequiredArgsConstructor
public class LibrarianController {

    private final LibrarianService librarianService;

    @PostMapping(value = "/saveNewLibrarian")
    public ResponseEntity<?> saveNewLibrarian(@RequestBody LibrarianDTO librarianDTO) {
        librarianService.saveNewLibrarian(librarianDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getLibrarianById/{librarianId}")
    public ResponseEntity<LibrarianDTO> getLibrarianById(@PathVariable(name = "librarianId") long librarianId) {
        LibrarianDTO librarianDTO = librarianService.getLibrarianById(librarianId);
        return librarianDTO != null
                ? new ResponseEntity<>(librarianDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
