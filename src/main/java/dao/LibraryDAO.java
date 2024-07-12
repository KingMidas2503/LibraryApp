package dao;

import Service.Book;
import Service.Library;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class LibraryDAO {

    private JdbcTemplate jdbcTemplate;;

    public LibraryDAO() {
        jdbcTemplate = SpringJdbcConfig.getJdbcTemplate();
    }


    public void createBookTable(Library library) {
        String title = library.getTitle().replaceAll(" ", "_").toUpperCase();
        String sqlString = "CREATE TABLE BOOKS_OF_"
                + title + " (ID SERIAL PRIMARY KEY, TITLE VARCHAR(50) NOT NULL, AUTHOR VARCHAR(40) NOT NULL)";
        jdbcTemplate.execute("DROP TABLE IF EXISTS BOOKS_OF_" + title);
        jdbcTemplate.execute(sqlString);
        System.out.println("Создана таблица с книгами библиотеки \"" + library.getTitle() + "\"");
    }

    public void addBookInTable(Library library, Book book, int bookId) {
        String libraryTitle = library.getTitle().replaceAll(" ", "_").toUpperCase();
        String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
        String bookAuthor = book.getAuthor().replaceAll(" ", "_").toUpperCase();
        String sqlString = "INSERT INTO BOOKS_OF_" + libraryTitle + " (ID, TITLE, AUTHOR) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlString, bookId, bookTitle, bookAuthor);
        System.out.println("В таблицу с книгами библиотеки \"" + library.getTitle() + "\"" + " добавлена книга \"" + book.getTitle() + "\", автор " + book.getAuthor());
    }

    public HashMap<Integer, Book> selectBookFromTable(Library library, Book book) {
        HashMap<Integer, Book> selectedBook = new HashMap<>();

        String bookTitle = book.getTitle().replaceAll(" ", "_").toUpperCase();
        String sqlString = "SELECT ID, TITLE, AUTHOR FROM BOOKS_OF_"
                + library.getTitle().replaceAll(" ", "_").toUpperCase()
                + " WHERE TITLE = ?";

        List<Book> result = jdbcTemplate.query(sqlString, new Object[]{bookTitle}, new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                int bookId = rs.getInt("ID");
                Book book = new Book(rs.getString("TITLE"), rs.getString("AUTHOR"));
                book.setId(bookId);
                return book;
            }
        });
        selectedBook.put(result.get(0).getId(), result.get(0));

        return selectedBook;
    }


}
