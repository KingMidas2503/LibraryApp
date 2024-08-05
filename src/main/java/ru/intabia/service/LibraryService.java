package ru.intabia.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.intabia.dao.LibraryDAO;
import ru.intabia.dto.LibraryDTO;
import ru.intabia.models.Library;


@Service
@Getter
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryDAO libraryDAO;

    public LibraryDTO getLibraryById(long libraryId) {
        Library library = libraryDAO.getLibraryById(libraryId);
        return new LibraryDTO(library.getId(), library.getTitle());
    }
}
