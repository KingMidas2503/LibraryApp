import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Reader {

    private String name;

    HashMap<Integer, Book> booksInUse;

    int countOfBooksInUse;

    private String libraryNumber;

    public Reader(String name) {
        this.name = name;
        libraryNumber = "";
        booksInUse = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getLibraryNumber() {
        return libraryNumber;
    }

    void registerInLibrary(Library library) {
        if (libraryNumber == "") {
            Random rand = new Random();
            int number = rand.nextInt(10000);
            int i = 5 - String.valueOf(number).length();
            while (i > 0) {
                libraryNumber += "0";
                i--;
            }
            libraryNumber += String.valueOf(number);
            library.readersRegistry.put(libraryNumber, this);
            System.out.println("В библиотеке \"" + library.getTitle() + "\" новый читатель " + this.name + ". Ему присвоен регистрационный номер " + libraryNumber);
        }
        else {
            System.out.println("В библиотеку \"" + library.getTitle() + "\" снова пришел читаель " + this.name + ", регистрационный номер " + libraryNumber);
        }
    }

    void takeABook(HashMap<Integer, Book> availableBooks, int bookId) {
        if (!availableBooks.containsKey(bookId)) {
            return;
        }
        Book choosenBook = availableBooks.get(bookId);
        booksInUse.put(bookId, choosenBook);
        countOfBooksInUse = booksInUse.size();
        System.out.println("Читатель " + this.name + " взял почитать книгу \"" + choosenBook.getTitle() + "\" автора " + choosenBook.getAuthor() + ". Теперь у него " + countOfBooksInUse + " книг");
    }

    void returnTheBook(HashMap<Integer, Book> availableBooks, int bookId) {
        if (!booksInUse.containsKey(bookId)) {
            return;
        }
        Book returningBook = booksInUse.get(bookId);
        availableBooks.put(bookId, returningBook);
        booksInUse.remove(bookId);
        countOfBooksInUse = booksInUse.size();
        System.out.println("Читатель " + this.name + " вернул книгу \"" + returningBook.getTitle() + "\" автора " + returningBook.getAuthor() + ". Теперь у него " + countOfBooksInUse + " книг");
    }


}
