package ru.intabia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.intabia.dto.LibraryDTO;
import ru.intabia.service.LibraryService;


@RestController
@RequiredArgsConstructor
@RequestMapping("library")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping(value = "/getLibraryById/{libraryId}")
    public ResponseEntity<LibraryDTO> getLibraryById(@PathVariable(name = "libraryId") long libraryId) {
        LibraryDTO libraryDTO = libraryService.getLibraryById(libraryId);
        return libraryDTO != null
                ? new ResponseEntity<>(libraryDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
