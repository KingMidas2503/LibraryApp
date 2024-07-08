package Service;


import dao.LibraryDAO;
import dao.ReaderDAO;

import java.util.HashMap;

public class Librarian {

    private String name;

    private Library library;

    ReaderDAO readerDAO = new ReaderDAO();

    LibraryDAO libraryDAO = new LibraryDAO();

    public Librarian(String name, Library library) {
        this.name = name;
        this.library = library;
        library.workers.add(this);
    }

    public String getName() {
        return name;
    }
    public Library getLibrary() {
        return library;
    }

    public void giveABook(Reader reader, Book book) {
        if (!reader.hasBeenToTheLibrary) {
            readerDAO.createBookTable(reader);
            reader.hasBeenToTheLibrary = true;
        }
        reader.registerInLibrary(library);

        HashMap<Integer, Book> selectedBook = libraryDAO.selectBookFromTable(library, book);
        int id = selectedBook.keySet().iterator().next();
        Book newBook = selectedBook.get(id);
        int i = library.rent.start(reader, book, id, this);
        if (i > 0) {
            readerDAO.addBookInTable(reader, newBook, id);
        }
    }

    public void acceptTheBook(Reader reader, Book book) {
        if (!reader.hasBeenToTheLibrary) {
            System.out.println("Читатель " + reader.getName() + " еще ни разу не был в библиотеке. Поэтому ему нечего возвращать!");
            return;
        }
        reader.registerInLibrary(library);
        int i = library.rent.stop(reader, book, this);
        if (i > 0) {
            readerDAO.deleteBookFromTable(reader, book);

        }
    }

}
