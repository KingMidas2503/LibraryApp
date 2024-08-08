package ru.intabia.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.intabia.dao.LibrarianDAO;
import ru.intabia.dao.ReaderDAO;
import ru.intabia.dto.LibrarianDTO;
import ru.intabia.dto.ReaderDTO;
import ru.intabia.models.Librarian;
import ru.intabia.models.Reader;


@Service
@RequiredArgsConstructor
public class LibrarianService {

    private final LibrarianDAO librarianDAO;

    public void saveNewLibrarian(LibrarianDTO librarianDTO) {
        Librarian librarian = new Librarian(librarianDTO.getName());
        librarianDAO.saveNewLibrarian(librarian);
    }

    public LibrarianDTO getLibrarianById(long librarianId) {
        Librarian librarian = librarianDAO.getLibrarianById(librarianId);
        if (librarian != null) {
            return new LibrarianDTO(librarianId, librarian.getName());
        }
        return null;
    }
}
