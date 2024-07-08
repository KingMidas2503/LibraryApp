package Service;

import dao.RentDAO;

public class Rent {


    private RentDAO dao = new RentDAO();

    public Rent(Library library) {
        dao.createRentTable(library);
    }

    public int start(Reader reader, Book book, int bookId, Librarian librarian) {
        return dao.start(reader, book, bookId, librarian);
    }

    public int stop(Reader reader, Book book, Librarian librarian) {
        return dao.stop(reader, book, librarian);
    }

}
