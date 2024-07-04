package Service;

import java.util.ArrayList;

public class Rent {

    private Reader reader;

    private Librarian librarian;

    private static int rentId = 0;

    private ArrayList<String> rentalCatalog;

    public Rent(String readerName, String librarianName, Library library) {
        this.reader = new Reader(readerName);
        this.librarian = new Librarian(librarianName, library);
        rentalCatalog = new ArrayList<>();
    }

    public Reader getReader() {
        return reader;
    }
    public Librarian getLibrarian() {
        return librarian;
    }
    public ArrayList<String> getRentalCatalog() {
        return rentalCatalog;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }
    public void setRentalCatalog(ArrayList<String> rentalCatalog) {
        this.rentalCatalog = rentalCatalog;
    }

    public void startARent(int bookId) {
        if (librarian.getLibrary().isEmpty()) {
            System.out.println("В библиотеке ни фига нет. Аренда невозможна.");
        }
        else {
            librarian.giveABook(reader, bookId);
        }
        rentId++;
        int i = 5 - String.valueOf(rentId).length();
        String rentDescription = "Сделка№";
        for (; i > 0; i--) {
            rentDescription += "0";
        }
        rentDescription += String.valueOf(rentId);
        rentDescription += "_Библиотекарь:" + this.librarian.getName() + "_" + "Читатель:" + this.reader.getName();
        rentalCatalog.add(rentDescription);

    }

    public void stopTheRent(int bookId) {
        if (reader.booksInUse.isEmpty()) {
            System.out.println("У читателя нет никаких книг. Он не начинал аренду, а значит, не сможет ее закончить.");
        }
        else {
            librarian.acceptTheBook(reader, bookId);
        }
    }
}
