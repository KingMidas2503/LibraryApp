package service;

import dao.LibraryDAO;
import dto.BookDTO;
import dto.RentDTO;
import lombok.Getter;
import models.Book;
import models.Library;
import models.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Getter
public class LibraryService {

    @Autowired
    private LibraryDAO libraryDAO = new LibraryDAO();
    Map<String, ReaderService> readersRegistry = new HashMap<>();
    List<LibrarianService> workers = new ArrayList<>();

    private long id;
    private List<BookDTO> books = new ArrayList<>();
    private String title;


    public LibraryService(String title) {
        this.title = title;
        Library library = new Library(title);
        libraryDAO.saveNewLibrary(library);
        this.id = library.getId();
    }

    public void addNewBook(Book book) {
        libraryDAO.saveNewBook(book, id);
        books.add(new BookDTO(book.getId(), book.getTitle(), book.getAuthor()));
    }

    public List<RentDTO> showRentTable() {
        List<Rent> rents = libraryDAO.showAllRents(this.id);
        List<RentDTO> rentDTOs = new ArrayList<>();
        for (Rent rent : rents) {
            RentDTO rentDTO = new RentDTO(rent.getId(), rent.getReaderId(), rent.getBookId(), rent.getLibraryId(), rent.isActive());
            rentDTOs.add(rentDTO);
        }
        return rentDTOs;
    }



}
