import java.util.ArrayList;
import java.util.Random;

public class Reader {

    private String name;

    ArrayList<Book> booksInUse;

    int countOfBooksInUse;

    private String libraryNumber;

    public Reader(String name) {
        this.name = name;
        libraryNumber = "";
        booksInUse = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getLibraryNumber() {
        return libraryNumber;
    }

    public void registerInLibrary(Library library) {
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

    public int chooseABook(ArrayList<Book> availableBooks) {
        if (availableBooks.size() == 0) {
            return -1;
        }
        Random rand = new Random();
        int index = rand.nextInt(availableBooks.size());
        Book choosenBook = availableBooks.get(index);
        System.out.println("Читатель " + this.name + " выбрал книгу \"" + choosenBook.getTitle() + "\" автора " + choosenBook.getAuthor());
        return index;
    }
    public void takeABook(Book book) {
        booksInUse.add(book);
        countOfBooksInUse = booksInUse.size();
        System.out.println("Читатель " + this.name + " взял почитать книгу \"" + book.getTitle() + "\" автора " + book.getAuthor() + ". Теперь у него " + countOfBooksInUse + " книг");
    }

    public void returnABook(Book book) {
        if (!booksInUse.contains(book)) {
            return;
        }
    }


}
