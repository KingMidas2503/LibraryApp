package ru.intabia.service;


import ru.intabia.dao.LibraryDAO;
import ru.intabia.dto.BookDTO;
import ru.intabia.dto.RentDTO;
import lombok.Getter;
import ru.intabia.models.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Getter
public class LibraryService {

    private LibraryDAO libraryDAO = new LibraryDAO();
    Map<String, Reader> readersRegistry = new HashMap<>();
    List<Librarian> workers = new ArrayList<>();
    private List<BookDTO> books = new ArrayList<>();

    public void addNewBook(BookDTO bookDTO, long libraryId) {
        Book book = new Book(bookDTO.getTitle(), bookDTO.getAuthor());
        libraryDAO.saveNewBook(book, libraryId);
        books.add(bookDTO);
    }

    public List<RentDTO> showRentTable(long libraryId) {
        List<Rent> rents = libraryDAO.showAllRents(libraryId);
        List<RentDTO> rentDTOs = new ArrayList<>();
        for (Rent rent : rents) {
            RentDTO rentDTO = new RentDTO(rent.getId(), rent.getReaderId(), rent.getBookId(), rent.getLibraryId(), rent.isActive());
            rentDTOs.add(rentDTO);
        }
        return rentDTOs;
    }



}
