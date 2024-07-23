package models;

import java.util.Random;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String name;
    private String libraryNumber;
    @Column
    boolean hasBeenToTheLibrary;


    public Reader(String name) {
        this.name = name;
        libraryNumber = "";
        hasBeenToTheLibrary = false;
    }
    
    private void registerInLibrary(Library library) {
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
            this.hasBeenToTheLibrary = true;
            System.out.println("В библиотеке \"" + library.getTitle() + "\" новый читатель " + this.name + ". Ему присвоен регистрационный номер " + libraryNumber);
        }
        else {
            System.out.println("В библиотеку \"" + library.getTitle() + "\" снова пришел читатель " + this.name + ", регистрационный номер " + libraryNumber);
        }
    }

    public void takeABook(Library library, Book book) {
        this.registerInLibrary(library);
        int size = library.workers.size();
        if (size == 0) {
            System.out.println("В библиотеке " + library.getTitle() + " нит библиотекарей. Поэтому библиотека не работает и книгу получить не получится.");
            return;
        }
        int randIndex = new Random().nextInt(size);
        Librarian librarian = library.workers.get(randIndex);
        librarian.giveABook(this, book);
    }

    public void returnTheBook(Library library, Book book) {
        this.registerInLibrary(library);
        int size = library.workers.size();
        int randIndex = new Random().nextInt(size);
        Librarian librarian = library.workers.get(randIndex);
        librarian.acceptTheBook(this, book);
    }


}
