package ru.intabia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.intabia.dto.ReaderDTO;
import ru.intabia.service.ReaderService;


@RestController
@RequestMapping("reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @PostMapping(value = "/saveNewReader")
    public ResponseEntity<?> saveNewReader(@RequestBody ReaderDTO readerDTO) {
        readerService.saveNewReader(readerDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getReaderById/{readerId}")
    public ResponseEntity<ReaderDTO> getReaderById(@PathVariable(name = "readerId") long readerId) {
        ReaderDTO readerDTO = readerService.getReaderById(readerId);
        return readerDTO != null
                ? new ResponseEntity<>(readerDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
