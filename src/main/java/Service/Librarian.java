package Service;


import dao.LibraryDAO;
import dao.ReaderDAO;

import java.util.HashMap;
import java.util.NoSuchElementException;

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
    public void setLibrary(Library library) {
        this.library = library;
    }

    public void giveABook(Reader reader, Book book) {
        if (!reader.hasBeenToTheLibrary) {
            readerDAO.createBookTable(reader);
            reader.hasBeenToTheLibrary = true;
        }
        reader.registerInLibrary(library);
        try {
            HashMap<Integer, Book> selectedBook = libraryDAO.selectBookFromTable(library, book);
            int id = selectedBook.keySet().iterator().next();
            Book newBook = selectedBook.get(id);
            readerDAO.addBookInTable(reader, newBook, id);
            System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю " + reader.getName() + " библиотекарем " + this.getName() + " в библиотеке \"" + library.getTitle() + "\"");
        } catch (NoSuchElementException e) {
            System.out.println("В библиотеке \"" + library.getTitle() + "\" сейчас нет книги \"" + book.getTitle() + "\". Читатель " + reader.getName() + " ее получить не может.");
        }
    }

    public void acceptTheBook(Reader reader, Book book) {
        if (!reader.hasBeenToTheLibrary) {
            System.out.println("Читатель " + reader.getName() + " еще ни разу не был в библиотеке. Поэтому ему нечего возвращать!");
            return;
        }
        reader.registerInLibrary(library);
        try {
            HashMap<Integer, Book> selectedBook = readerDAO.selectBookFromTable(reader, book);
            int id = selectedBook.keySet().iterator().next();
            Book newBook = selectedBook.get(id);
            libraryDAO.addBookInTable(library, newBook, id);
            System.out.println("Читатель " + reader.getName() + " вернул книгу \"" + book.getTitle() + "\" в библиотеку \"" + library.getTitle() + "\". Книгу принял библиотекарь " + this.getName());
        } catch (NoSuchElementException e) {
            System.out.println("У читателя " + reader.getName() + " сейчас нет книги \"" + book.getTitle() + "\" в пользовании. Так что хрен он ее вернет. Нельзя вернуть то, чего у тебя нет.");
        }
    }

}
