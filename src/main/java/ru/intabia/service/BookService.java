package ru.intabia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.intabia.dao.BookDAO;
import ru.intabia.dto.BookDTO;
import ru.intabia.models.Book;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDAO bookDAO;

    public BookDTO lookAtBook(long libraryId, long bookId) {
        Book book = bookDAO.showABook(libraryId, bookId, true);
        if (book != null) {
            return new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
        }
        return null;
    }

    public List<BookDTO> lookAtAllBooks(long libraryId) {
        List<Book> bookModels = bookDAO.showAllBooks(libraryId);
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
        book.setIsUsingNow(false);
        bookDAO.saveNewBook(book, libraryId);
    }

    public BookDTO takeABook(long libraryId, long bookId, long readerId) {
        Book book = bookDAO.giveABook(libraryId, bookId, readerId);
        if (book != null) {
            return new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
        }
        return null;
    }

    public void returnTheBook(long libraryId, long bookId, long readerId) {
        bookDAO.acceptTheBook(libraryId, bookId, readerId);
    }
}

