package ru.intabia.service;

import ru.intabia.dao.LibraryDAO;
import ru.intabia.dao.ReaderDAO;
import ru.intabia.dto.BookDTO;
import ru.intabia.dto.LibraryDTO;
import lombok.Getter;
import ru.intabia.dto.ReaderDTO;
import ru.intabia.models.Book;
import ru.intabia.models.Library;
import ru.intabia.models.Reader;
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

    public void saveNewReader(ReaderDTO readerDTO) {
        Reader reader = new Reader(readerDTO.getName());
        readerDAO.saveNewReader(reader);
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
