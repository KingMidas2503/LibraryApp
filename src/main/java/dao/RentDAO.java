package dao;

import Service.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class RentDAO {

    private JdbcTemplate jdbcTemplate;;

    private String databaseName;

    private Map<Book, Reader> statusOfRent = new HashMap<>();

    public RentDAO() {
        jdbcTemplate = SpringJdbcConfig.getJdbcTemplate();
        databaseName = "";
    }

    public void createRentTable(Library library) {
        String libraryTitle = library.getTitle().replaceAll(" ", "_").toUpperCase();
        databaseName = "RENT_OF_" + libraryTitle;
        String sqlString = "CREATE TABLE " + databaseName
                + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL, READER_NAME VARCHAR(40) NOT NULL, IS_ACTIVE BOOLEAN NOT NULL)";
        jdbcTemplate.execute("DROP TABLE IF EXISTS " + databaseName);
        jdbcTemplate.execute(sqlString);
        System.out.println("Создана таблица для записей об аренде книг в библиотеке \"" + library.getTitle() + "\"");

    }
    public int start(Reader reader, Book book, int bookId, Librarian librarian) {
        try {
            String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
            String bookAuthor = book.getAuthor().replaceAll(" ", "_").toUpperCase();
            String readerName = reader.getName().replaceAll(" ", "_").toUpperCase();
            String checkSqlString = "SELECT ID, TITLE, AUTHOR, READER_NAME, IS_ACTIVE FROM " + databaseName + " WHERE TITLE = \'" + bookTitle + "\'";
            String sqlInsert = "INSERT INTO " + databaseName + " (ID, TITLE, AUTHOR, READER_NAME, IS_ACTIVE) VALUES (?, ?, ?, ?, ?)";
            String sqlUpdate = "UPDATE " + databaseName + " SET IS_ACTIVE = TRUE WHERE TITLE = \'" + bookTitle + "\'";
            boolean isActive = true;
            Map<List<String>, Boolean> extr = jdbcTemplate.query(checkSqlString, new ResultSetExtractor<Map <List<String>, Boolean>>() {
                @Override
                public HashMap<List<String>, Boolean> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    HashMap<List<String>, Boolean> extracted = new HashMap<>();
                    while (rs.next()) {
                        String bookTitle = rs.getString("TITLE");
                        String bookAuthor = rs.getString("AUTHOR");
                        String readerName = rs.getString("READER_NAME");
                        Boolean isActive = rs.getBoolean("IS_ACTIVE");
                        List<String> list = new ArrayList<>();
                        list.add(bookTitle);
                        list.add(bookAuthor);
                        list.add(readerName);
                        extracted.put(list, isActive);
                    }
                    return extracted;
                }
            });
            if (extr.isEmpty()) {
                jdbcTemplate.update(sqlInsert, bookId, bookTitle, bookAuthor, readerName, true);
                statusOfRent.put(book, reader);
                System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю " + reader.getName() + " библиотекарем " + librarian.getName() + " в библиотеке \"" + librarian.getLibrary().getTitle() + "\"");
                return 1;
            } else {
                isActive = extr.values().iterator().next();
                if (!isActive) {
                    jdbcTemplate.update(sqlUpdate);
                    statusOfRent.put(book, reader);
                    System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю " + reader.getName() + " библиотекарем " + librarian.getName() + " в библиотеке \"" + librarian.getLibrary().getTitle() + "\"");
                    return 1;
                } else {
                    System.out.println("В библиотеке \"" + librarian.getLibrary().getTitle() + "\" сейчас нет книги \"" + book.getTitle() + "\". Она находится в аренде. Читатель " + reader.getName() + " ее получить не может.");
                    return -1;
                }
            }


        } catch (NullPointerException e) {
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
                jdbcTemplate.update(sqlString);
                statusOfRent.put(book, reader);
                System.out.println("Читатель " + reader.getName() + " вернул книгу \"" + book.getTitle() + "\" в библиотеку \"" + librarian.getLibrary().getTitle() + "\". Книгу принял библиотекарь " + librarian.getName());
                return 1;
            } catch (Exception e) {
                System.out.print("Блин, возникла ошибка: " + e.getMessage());
                return -1;
            }
        }
    }
}
