package ru.intabia.controllers;

import ru.intabia.dto.RentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.intabia.service.LibraryService;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @RequestMapping("/libraryProject/api/v1/showRentTable")
    public List<RentDTO> showRentTable() {
        return libraryService.showRentTable();
    }
}
