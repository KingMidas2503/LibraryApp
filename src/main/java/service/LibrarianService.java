package service;

import dao.LibrarianDAO;
import dao.LibraryDAO;
import dto.BookDTO;
import dto.LibraryDTO;
import dto.ReaderDTO;
import lombok.Getter;
import models.Book;
import models.Librarian;
import models.Library;
import models.Reader;
import java.util.ArrayList;
import java.util.List;


public class LibrarianService {

    @Getter
    private long id;
    @Getter
    private String name;
    private LibraryDAO libraryDAO = new LibraryDAO();;
    private LibrarianDAO librarianDAO = new LibrarianDAO();


    public LibrarianService(String name) {
        this.name = name;
        Librarian librarian = new Librarian(name);
        librarianDAO.saveNewLibrarian(librarian);
        this.id = librarian.getId();
    }

    public BookDTO lookAtBook(long libraryId, long bookId) {
        Book book = libraryDAO.showABook(libraryId, bookId, true);
        if (book != null) {
            BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
            return bookDTO;
        }
        return null;
    }

    public List<BookDTO> lookAtAllBooks(long libraryId) {
        List<Book> bookModels = libraryDAO.showAllBooks(libraryId, true);
        List<BookDTO> bookDTOs = new ArrayList<>();
        if (bookModels != null && !bookModels.isEmpty()) {
            for (Book book : bookModels) {
                bookDTOs.add(new BookDTO(book.getId(), book.getTitle(), book.getAuthor()));
            }
            return bookDTOs;
        }
        return null;
    }

    public void addBook(long libraryId, BookDTO bookDTO) {
        Book book = new Book(bookDTO.getTitle(), bookDTO.getAuthor());
        book.setUsingNow(false);
        libraryDAO.saveNewBook(book, libraryId);
    }

    public ReaderDTO getReaderById(long readerId) {
        Reader reader = libraryDAO.getReaderById(readerId);
        if (reader != null) {
            ReaderDTO readerDTO = new ReaderDTO(readerId, reader.getName());
            return readerDTO;
        }
        return null;
    }

    public LibraryDTO getLibraryById(long libraryId) {
        Library library = libraryDAO.getLibraryById(libraryId);
        LibraryDTO libraryDTO = new LibraryDTO(library.getId(), library.getTitle());
        return libraryDTO;
    }

}
