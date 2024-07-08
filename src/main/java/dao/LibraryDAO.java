package dao;

import Service.Book;
import Service.Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LibraryDAO {

    private Connection libCon;

    public LibraryDAO() {
        libCon = LibConnection.getLibConnection();
    }


    public void createBookTable(Library library) {
        try {
            String sqlString = "CREATE TABLE BOOKS_OF_"
                    + library.getTitle().replaceAll(" ", "_").toUpperCase()
                    + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL)";
            PreparedStatement ps = libCon.prepareStatement(sqlString);
            ps.execute();
            System.out.println("Создана таблица с книгами библиотеки \"" + library.getTitle() + "\"");
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }

    }
    public void addBookInTable(Library library, Book book, int bookId) {
        try {
            String libraryTitle = library.getTitle().replaceAll(" ", "_").toUpperCase();
            String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
            String bookAuthor = book.getAuthor().replaceAll(" ", "_").toUpperCase();
            String sqlString = "INSERT INTO BOOKS_OF_" + libraryTitle + " (ID, TITLE, AUTHOR) VALUES (?, ?, ?)";
            PreparedStatement ps = libCon.prepareStatement(sqlString);
            ps.setInt(1, bookId);
            ps.setString(2, bookTitle);
            ps.setString(3, bookAuthor);
            ps.execute();
            System.out.println("В таблицу с книгами библиотеки \"" + library.getTitle() + "\"" + " добавлена книга \"" + book.getTitle() + "\", автор " + book.getAuthor());
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }
    }

    public HashMap<Integer, Book> selectBookFromTable(Library library, Book book) {
        HashMap<Integer, Book> selectedBook = new HashMap<>();
        try {
            String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
            String sqlString = "SELECT ID, TITLE, AUTHOR FROM BOOKS_OF_"
                    + library.getTitle().replaceAll(" ", "_").toUpperCase()
                    + " WHERE TITLE = " + "\'" + bookTitle + "\'";

            PreparedStatement select = libCon.prepareStatement(sqlString);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                selectedBook.put(id, new Book(title, author));
            }
        } catch (SQLException e) {
            System.out.println("Блин, возникла ошибка: " + e.getMessage());
        }

        return selectedBook;
    }


}
