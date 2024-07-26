package service;

import dao.LibraryDAO;
import dao.ReaderDAO;
import dto.BookDTO;
import dto.LibraryDTO;
import lombok.Getter;
import models.Book;
import models.Library;
import models.Reader;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReaderService {
    @Getter
    private long id;
    @Getter
    private String name;
    @Getter
    private boolean hasBeenToTheLibrary;
    private LibraryDAO libraryDAO = new LibraryDAO();
    private ReaderDAO readerDAO = new ReaderDAO();

    public ReaderService(String name) {
        this.name = name;
        hasBeenToTheLibrary = false;
        Reader reader = new Reader(name);
        readerDAO.saveNewReader(reader);
        this.id = reader.getId();
    }

    public BookDTO takeABook(long libraryId, long bookId) {
        Book book = libraryDAO.giveABook(libraryId, bookId, this.id);
        this.hasBeenToTheLibrary = true;
        if (book != null) {
            BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
            return bookDTO;
        }
        return null;
    }

    public void returnTheBook(long libraryId, long bookId) {
        libraryDAO.acceptTheBook(libraryId, bookId, this.id);
    }

    public BookDTO lookAtBook(long libraryId, long bookId) {
        Book book = libraryDAO.showABook(libraryId, bookId, false);
        this.hasBeenToTheLibrary = true;
        if (book != null) {
            BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
            return bookDTO;
        }
        return null;
    }

    public List<BookDTO> lookAtAllBooks(long libraryId) {
        List<Book> bookModels = libraryDAO.showAllBooks(libraryId, false);
        List<BookDTO> bookDTOs = new ArrayList<>();
        this.hasBeenToTheLibrary = true;
        if (bookModels != null && !bookModels.isEmpty()) {
            for (Book book : bookModels) {
                bookDTOs.add(new BookDTO(book.getId(), book.getTitle(), book.getAuthor()));
            }
            return bookDTOs;
        }
        return null;
    }

    public LibraryDTO getLibraryById(long libraryId) {
        Library library = libraryDAO.getLibraryById(libraryId);
        LibraryDTO libraryDTO = new LibraryDTO(library.getId(), library.getTitle());
        return libraryDTO;
    }

}
