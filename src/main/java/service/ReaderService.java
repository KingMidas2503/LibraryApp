package service;

import dao.LibraryDAO;
import dto.BookDTO;
import lombok.Getter;
import models.Book;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {
    @Getter
    private long id;
    @Getter
    private String name;
    private LibraryDAO libraryDAO;

    public ReaderService(String name) {
        this.name = name;
        libraryDAO = new LibraryDAO();
    }



    public BookDTO takeABook(long libraryId, long bookId) {
        Book book = libraryDAO.giveABook(libraryId, bookId, this.id);
        BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
        return bookDTO;
    }

    public void returnTheBook(long libraryId, BookDTO bookDTO) {
        long bookId = bookDTO.getId();
        libraryDAO.acceptTheBook(libraryId, bookId, this.id);
    }

    public BookDTO lookAtBook(long libraryId, long bookId) {
        Book book = libraryDAO.showABook(libraryId, bookId, false);
        BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
        return bookDTO;
    }

}
