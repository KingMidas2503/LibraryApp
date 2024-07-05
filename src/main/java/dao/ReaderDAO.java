package dao;

import Service.Book;
import Service.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ReaderDAO {

    private Connection readCon;

    public ReaderDAO() {
        readCon = LibConnection.getLibConnection();
    }

    public void createBookTable(Reader reader) {
        try {
            String sqlString = "CREATE TABLE BOOKS_IN_USE_OF_"
                    + reader.getName().replaceAll(" ", "_").toUpperCase()
                    + " ("
                    + " ID SERIAL PRIMARY KEY, "
                    + " TITLE VARCHAR(50) NOT NULL,"
                    + " AUTHOR VARCHAR(30) NOT NULL"
                    + ")";
            PreparedStatement ps = readCon.prepareStatement(sqlString);
            ps.execute();
            System.out.println("Создана таблица для книг в пользовании читателя " + reader.getName());
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }

    }
    public void addBookInTable(Reader reader, Book book, int bookId) {
        try {
            String title = book.getTitle().replaceAll(" ", "_").toUpperCase();
            String author = book.getAuthor().replaceAll(" ", "_").toUpperCase();
            String sqlString = "INSERT INTO BOOKS_IN_USE_OF_"
                    + reader.getName().replaceAll(" ", "_").toUpperCase()
                    +" (ID, TITLE, AUTHOR) VALUES ("
                    + bookId + ", \'"
                    + title + "\', \'"
                    + author
                    + "\')";
            PreparedStatement ps = readCon.prepareStatement(sqlString);
            ps.execute();
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }
    }

    public HashMap<Integer, Book> selectBookFromTable(Reader reader, Book book) {
        HashMap<Integer, Book> selectedBooks = new HashMap<>();
        String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
        try {
            String selectString = "SELECT ID, TITLE, AUTHOR FROM BOOKS_IN_USE_OF_"
                    + reader.getName().replaceAll(" ", "_").toUpperCase()
                    + " WHERE TITLE = " + "\'" + bookTitle + "\'";
            String deleteString = "DELETE FROM BOOKS_IN_USE_OF_"
                    + reader.getName().replaceAll(" ", "_").toUpperCase()
                    + " WHERE TITLE = " + "\'" + bookTitle + "\'";
            PreparedStatement select = readCon.prepareStatement(selectString);
            ResultSet rs = select.executeQuery();
            PreparedStatement delete = readCon.prepareStatement(deleteString);
            delete.execute();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                selectedBooks.put(id, new Book(title, author));
            }
        } catch (SQLException e) {
            System.out.println("Блин, возникла ошибка: " + e.getMessage());
        }

        return selectedBooks;
    }

}
