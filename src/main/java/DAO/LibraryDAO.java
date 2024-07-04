package DAO;

import Service.Book;
import Service.Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            String sqlString = "INSERT INTO BOOKS_OF_"
                    + library.getTitle().replaceAll(" ", "_").toUpperCase()
                    +" (ID, TITLE, AUTHOR) VALUES ("
                    + bookId + ", \'"
                    + book.getTitle().replaceAll(" ", "_") + "\', \'"
                    + book.getAuthor().replaceAll(" ", "_")
                    + "\')";
            PreparedStatement ps = libCon.prepareStatement(sqlString);
            ps.execute();
            System.out.println("В таблицу с книгами библиотеки \"" + library.getTitle() + "\"" + " добавлена книга \"" + book.getTitle() + "\"");
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }
    }

}
