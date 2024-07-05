package dao;

import Service.Book;
import Service.Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class LibraryDAO {

    private Connection libCon;

    public LibraryDAO() {
        libCon = LibConnection.getLibConnection();
    }


    public void createBookTable(Library library) {
        try {
            String sqlString = "CREATE TABLE BOOKS_OF_"
                    + library.getTitle().replaceAll(" ", "_").toUpperCase()
                    + " ("
                    + " ID SERIAL PRIMARY KEY, "
                    + " TITLE VARCHAR(50) NOT NULL,"
                    + " AUTHOR VARCHAR(30) NOT NULL"
                    + ")";
            PreparedStatement ps = libCon.prepareStatement(sqlString);
            ps.execute();
            System.out.println("Создана таблица с книгами библиотеки \"" + library.getTitle() + "\"");
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }

    }
    public void addBookInTable(Library library, Book book, int bookId) {
        try {
            String title = book.getTitle().replaceAll(" ", "_").toUpperCase();
            String author = book.getAuthor().replaceAll(" ", "_").toUpperCase();
            String sqlString = "INSERT INTO BOOKS_OF_"
                    + library.getTitle().replaceAll(" ", "_").toUpperCase()
                    +" (ID, TITLE, AUTHOR) VALUES ("
                    + bookId + ", \'"
                    + title + "\', \'"
                    + author
                    + "\')";
            PreparedStatement ps = libCon.prepareStatement(sqlString);
            ps.execute();
            System.out.println("В таблицу с книгами библиотеки \"" + library.getTitle() + "\"" + " добавлена книга \"" + book.getTitle() + "\"");
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }
    }

    public HashMap<Integer, Book> selectBookFromTable(Library library, Book book) {
        HashMap<Integer, Book> selectedBook = new HashMap<>();
        try {
            String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
            String selectString = "SELECT ID, TITLE, AUTHOR FROM BOOKS_OF_"
                    + library.getTitle().replaceAll(" ", "_").toUpperCase()
                    + " WHERE TITLE = " + "\'" + bookTitle + "\'";
            String deleteString = "DELETE FROM BOOKS_OF_"
                    + library.getTitle().replaceAll(" ", "_").toUpperCase()
                    + " WHERE TITLE = " + "\'" + bookTitle + "\'";
            PreparedStatement select = libCon.prepareStatement(selectString);
            ResultSet rs = select.executeQuery();
            PreparedStatement delete = libCon.prepareStatement(deleteString);
            delete.execute();
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
