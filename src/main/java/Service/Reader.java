package Service;

import java.util.Random;
import lombok.Getter;

public class Reader {
    @Getter
    private String name;
    @Getter
    private String libraryNumber;

    boolean hasBeenToTheLibrary;


    public Reader(String name) {
        this.name = name;
        libraryNumber = "";
        hasBeenToTheLibrary = false;
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
            System.out.println("В библиотеку \"" + library.getTitle() + "\" снова пришел читатель " + this.name + ", регистрационный номер " + libraryNumber);
        }
    }

    void takeABook(Library library, Book book) {
        int size = library.workers.size();
        int randIndex = new Random().nextInt(size);
        Librarian librarian = library.workers.get(randIndex);
        librarian.giveABook(this, book);
    }

    void returnTheBook(Library library, Book book) {
        int size = library.workers.size();
        int randIndex = new Random().nextInt(size);
        Librarian librarian = library.workers.get(randIndex);
        librarian.acceptTheBook(this, book);
    }


}
