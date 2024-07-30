package ru.intabia.service;

public class BookServiceBuilder {
    private String title;
    private String author;

    public BookServiceBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookServiceBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookService createBookService() {
        return new BookService(title, author);
    }
}