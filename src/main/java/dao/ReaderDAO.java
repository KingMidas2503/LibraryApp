package dao;

import Service.Book;
import Service.Reader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReaderDAO {

    private JdbcTemplate jdbcTemplate;

    public ReaderDAO() {
        jdbcTemplate = SpringJdbcConfig.getJdbcTemplate();
    }

    public void createBookTable(Reader reader) {
        String readerName = reader.getName().replaceAll(" ", "_").toUpperCase();
        String sqlString = "CREATE TABLE BOOKS_IN_USE_OF_"
                + readerName
                + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL)";
        jdbcTemplate.update("DROP TABLE IF EXISTS BOOKS_IN_USE_OF_" + readerName);
        jdbcTemplate.execute(sqlString);
        System.out.println("Создана таблица для книг в пользовании читателя " + reader.getName());
    }


    public void addBookInTable(Reader reader, Book book, int bookId) {
        String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
        String bookAuthor = book.getAuthor().replaceAll(" ", "_").toUpperCase();
        String readerName = reader.getName().replaceAll(" ", "_").toUpperCase();
        String sqlString = "INSERT INTO BOOKS_IN_USE_OF_" + readerName + " (ID, TITLE, AUTHOR) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlString, bookId, bookTitle, bookAuthor);
    }

    public void deleteBookFromTable(Reader reader, Book book) {
        String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
        String readerName = reader.getName().replaceAll(" ", "_").toUpperCase();
        String sqlString = "DELETE FROM BOOKS_IN_USE_OF_" + readerName + " WHERE TITLE = \'" + bookTitle + "\'";
        jdbcTemplate.update(sqlString);
    }
}
