package dao;

import Service.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RentDAO {

    private Connection rentCon;

    private String databaseName;

    private HashMap<Book, Reader> statusOfRent = new HashMap<>();

    public RentDAO() {
        rentCon = LibConnection.getLibConnection();
        databaseName = "";
    }

    public void createRentTable(Library library) {
        try {
            String libraryTitle = library.getTitle().replaceAll(" ", "_").toUpperCase();
            String sqlString = "CREATE TABLE RENT_OF_"
                    + libraryTitle
                    + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL, READER_NAME VARCHAR(40) NOT NULL, IS_ACTIVE BOOLEAN NOT NULL)";
            PreparedStatement ps = rentCon.prepareStatement(sqlString);
            ps.execute();
            databaseName = "RENT_OF_" + libraryTitle;
            System.out.println("Создана таблица для записей об аренде книг в библиотеке \"" + library.getTitle() + "\"");
        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
        }

    }
    public int start(Reader reader, Book book, int bookId, Librarian librarian) {
        try {
            String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
            String bookAuthor = book.getAuthor().replaceAll(" ", "_").toUpperCase();
            String readerName = reader.getName().replaceAll(" ", "_").toUpperCase();
            String checkSqlString = "SELECT ID, TITLE, AUTHOR, READER_NAME, IS_ACTIVE FROM " + databaseName + " WHERE TITLE = \'" + bookTitle + "\'";
            PreparedStatement select = rentCon.prepareStatement(checkSqlString);
            ResultSet rs = select.executeQuery();
            String sqlInsert = "INSERT INTO " + databaseName + " (ID, TITLE, AUTHOR, READER_NAME, IS_ACTIVE) VALUES (?, ?, ?, ?, ?)";
            String sqlUpdate = "UPDATE " + databaseName + " SET IS_ACTIVE = TRUE WHERE TITLE = \'" + bookTitle + "\'";
            PreparedStatement ps;
            String title = "";
            String author = "";
            String name = "";
            boolean isActive = true;
            while (rs.next()) {
                int id = rs.getInt("ID");
                title = rs.getString("TITLE");
                author = rs.getString("AUTHOR");
                name = rs.getString("READER_NAME");
                isActive = rs.getBoolean("IS_ACTIVE");
            }
            if ((title.equals("") && author.equals("") && name.equals(""))) {
                ps = rentCon.prepareStatement(sqlInsert);
                ps.setInt(1, bookId);
                ps.setString(2, bookTitle);
                ps.setString(3, bookAuthor);
                ps.setString(4, readerName);
                ps.setBoolean(5, true);
                ps.execute();
                statusOfRent.put(book, reader);
                System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю " + reader.getName() + " библиотекарем " + librarian.getName() + " в библиотеке \"" + librarian.getLibrary().getTitle() + "\"");
                return 1;
            } else if (!isActive) {
                ps = rentCon.prepareStatement(sqlUpdate);
                ps.execute();
                statusOfRent.put(book, reader);
                System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю " + reader.getName() + " библиотекарем " + librarian.getName() + " в библиотеке \"" + librarian.getLibrary().getTitle() + "\"");
                return 1;
            } else {
                System.out.println("В библиотеке \"" + librarian.getLibrary().getTitle() + "\" сейчас нет книги \"" + book.getTitle() + "\". Она находится в аренде. Читатель " + reader.getName() + " ее получить не может.");
                return -1;
            }


        } catch (SQLException e) {
            System.out.print("Блин, возникла ошибка: " + e.getMessage());
            return -1;
        }
    }

    public int stop(Reader reader, Book book, Librarian librarian) {
        if (statusOfRent.containsKey(book) && !statusOfRent.get(book).equals(reader)) {
            System.out.println("У читателя " + reader.getName() + " сейчас нет книги \"" + book.getTitle() + "\" в пользовании. Так что хрен он ее вернет. Нельзя вернуть то, чего у тебя нет.");
            return -1;

        } else {
            try {
                String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
                String sqlString = "UPDATE " + databaseName + " SET IS_ACTIVE = FALSE WHERE TITLE = \'" + bookTitle + "\'";
                PreparedStatement ps = rentCon.prepareStatement(sqlString);
                ps.execute();
                statusOfRent.put(book, new Reader("noname"));
                System.out.println("Читатель " + reader.getName() + " вернул книгу \"" + book.getTitle() + "\" в библиотеку \"" + librarian.getLibrary().getTitle() + "\". Книгу принял библиотекарь " + librarian.getName());
                return 1;
            } catch (SQLException e) {
                System.out.print("Блин, возникла ошибка: " + e.getMessage());
                return -1;
            }
        }
    }
}
