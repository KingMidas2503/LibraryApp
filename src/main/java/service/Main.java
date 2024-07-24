package service;

import models.Book;


public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome, king Midas!");

        ReaderService reader = new ReaderService("Вася Пупкин");
        LibraryService basedLibrary = new LibraryService("The most based library");
        Book godfather = new Book("Крестный отец", "Марио Пьюзо");
        Book ilPrincipe = new Book("Государь", "Никколо Макиавелли");
        Book customersForLife = new Book("Клиенты на всю жизнь", "Пол Браун");
        Book designPatterns = new Book("Паттерны проектирования", "Эрих Гамма");
        Book philosophyOfJava = new Book("Философия Java", "Брюс Эккель");
        Book atlasShrugged = new Book("Атлант расправил плечи", "Айн Рэнд");
        basedLibrary.addNewBook(godfather, basedLibrary.getId());
        basedLibrary.addNewBook(ilPrincipe, basedLibrary.getId());
        basedLibrary.addNewBook(customersForLife, basedLibrary.getId());
        basedLibrary.addNewBook(designPatterns, basedLibrary.getId());
        basedLibrary.addNewBook(philosophyOfJava, basedLibrary.getId());
        basedLibrary.addNewBook(atlasShrugged, basedLibrary.getId());
        System.out.println(godfather.getId());
        System.out.println(basedLibrary.getId());
        System.out.println(reader.lookAtBook(0, 62));
    }
}