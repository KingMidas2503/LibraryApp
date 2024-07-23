package models;

import lombok.Getter;


public class Librarian {

    @Getter
    private String name;
    @Getter
    private Library library;
    @Getter
    private Rent rent;


    public Librarian(String name, Library library) {
        this.name = name;
        this.library = library;
        library.workers.add(this);
    }

    public void giveABook(Reader reader, Book book) {
        int check = library.takeBookFromLibrary(book);
        if (check > 0) {
            rent = new Rent(library, book, reader);
            rent.start();
            System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю " + reader.getName() + " в библиотеке " + library.getTitle() + " библиотекарем " + this.name);
        }
    }

    public void acceptTheBook(Reader reader, Book book) {
        if (!reader.hasBeenToTheLibrary) {
            System.out.println("Читатель " + reader.getName() + " еще ни разу не был в библиотеке. Поэтому ему нечего возвращать!");
            return;
        }
        int check = library.returnBookToLibrary(book);
        if (check > 0) {
            rent.stop();
            System.out.println("Читаель " + reader.getName() + " вернул книгу \"" + book.getTitle() + "\" в библиотеку " + library.getTitle() + ". Книгу принял библиотекарь " + this.name);
        }

    }

}
