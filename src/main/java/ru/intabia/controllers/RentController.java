package ru.intabia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.intabia.dto.RentDTO;
import ru.intabia.service.RentService;
import java.util.List;


@RestController
@RequestMapping("rent")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @GetMapping(value = "/showRentTable/{libraryId}")
    public ResponseEntity<List<RentDTO>> showRentTable(@PathVariable(name="libraryId") long libraryId) {
        List<RentDTO> rentDTOs = rentService.showRentTable(libraryId);
        return rentDTOs != null && !rentDTOs.isEmpty()
                ? new ResponseEntity<>(rentDTOs, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
