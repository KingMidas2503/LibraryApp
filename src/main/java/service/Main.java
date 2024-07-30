package service;

import dto.BookDTO;
import dto.RentDTO;
import models.Book;
import models.Reader;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome, king Midas!");

        ReaderService ivan = new ReaderService("Иван IV");
        System.out.println(ivan.getName());
        LibraryService anotherLibrary = new LibraryService("Какая-то библиотека");
        LibraryService basedLibrary = new LibraryService("The most based library");

        /*Book godfather = new Book("Крестный отец", "Марио Пьюзо");
        Book ilPrincipe = new Book("Государь", "Никколо Макиавелли");
        Book customersForLife = new Book("Клиенты на всю жизнь", "Пол Браун");
        Book designPatterns = new Book("Паттерны проектирования", "Эрих Гамма");
        Book philosophyOfJava = new Book("Философия Java", "Брюс Эккель");
        Book atlasShrugged = new Book("Атлант расправил плечи", "Айн Рэнд");
        basedLibrary.addNewBook(godfather);
        basedLibrary.addNewBook(ilPrincipe);
        basedLibrary.addNewBook(customersForLife);
        basedLibrary.addNewBook(designPatterns);
        basedLibrary.addNewBook(philosophyOfJava);
        basedLibrary.addNewBook(atlasShrugged);*/

        /*ReaderService petya = new ReaderService("Пётр I");
        LibrarianService alex = new LibrarianService("Александр II");*/



        System.out.println(basedLibrary.getId());
        System.out.println(ivan.getId());
        /*System.out.println(godfather.getId());
        System.out.println(ivan.lookAtBook(132, 494));
        System.out.println(ivan.takeABook(132, 494));
        ivan.returnTheBook(132, 494);
        System.out.println(petya.takeABook(132, 494));
        System.out.println(ivan.takeABook(132, 494));
        petya.returnTheBook(132, 494);

        List<BookDTO> booksForReader = petya.lookAtAllBooks(38);
        List<BookDTO> booksForLibrarian = alex.lookAtAllBooks(38);
        List<RentDTO> rentTable = basedLibrary.showRentTable();
        for (BookDTO book : booksForReader) {
            System.out.println(book);
        }
        System.out.println("------------------------------------");
        for (BookDTO book : booksForLibrarian) {
            System.out.println(book);
        }
        System.out.println("------------------------------------");
        for (RentDTO rent : rentTable) {
            System.out.println(rent);
        }*/

    }
}