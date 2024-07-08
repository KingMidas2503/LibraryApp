package dao;

import Service.Book;
import Service.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReaderDAO {

    private Connection readCon;

    public ReaderDAO() {
        readCon = LibConnection.getLibConnection();
    }

    public void createBookTable(Reader reader) {
        try {
            String sqlString = "CREATE TABLE BOOKS_IN_USE_OF_"
                    + reader.getName().replaceAll(" ", "_").toUpperCase()
                    + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL)";
            PreparedStatement ps = readCon.prepareStatement(sqlString);
            ps.execute();
            System.out.println("Создана таблица для книг в пользовании читателя " + reader.getName());
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }

    }
    public void addBookInTable(Reader reader, Book book, int bookId) {
        try {
            String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
            String bookAuthor = book.getAuthor().replaceAll(" ", "_").toUpperCase();
            String readerName = reader.getName().replaceAll(" ", "_").toUpperCase();
            String sqlString = "INSERT INTO BOOKS_IN_USE_OF_" + readerName + " (ID, TITLE, AUTHOR) VALUES (?, ?, ?)";
            PreparedStatement ps = readCon.prepareStatement(sqlString);
            ps.setInt(1, bookId);
            ps.setString(2, bookTitle);
            ps.setString(3, bookAuthor);
            ps.execute();
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }
    }

    public void deleteBookFromTable(Reader reader, Book book) {
        String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
        try {
            String sqlString = "DELETE FROM BOOKS_IN_USE_OF_"
                    + reader.getName().replaceAll(" ", "_").toUpperCase()
                    + " WHERE TITLE = " + "\'" + bookTitle + "\'";
            PreparedStatement delete = readCon.prepareStatement(sqlString);
            delete.execute();
        } catch (SQLException e) {
            System.out.println("Блин, возникла ошибка: " + e.getMessage());
        }

    }

}
