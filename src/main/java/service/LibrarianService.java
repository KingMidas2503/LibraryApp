package service;

import dao.LibraryDAO;
import dto.BookDTO;
import lombok.Getter;
import models.Book;


public class LibrarianService {

    @Getter
    private long id;
    @Getter
    private String name;
    private LibraryDAO libraryDAO;


    public LibrarianService(String name, LibraryService libraryService) {
        this.name = name;
        libraryDAO = new LibraryDAO();
    }

    public BookDTO lookAtBook(long libraryId, long bookId) {
        Book book = libraryDAO.showABook(libraryId, bookId, true);
        BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
        return bookDTO;
    }

    public void addBook(long libraryId, BookDTO bookDTO) {
        Book book = new Book(bookDTO.getTitle(), bookDTO.getAuthor());
        book.setUsingNow(false);
        libraryDAO.saveNewBook(book, libraryId);
    }

}
