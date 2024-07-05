package Service;

import java.util.ArrayList;

public class Rent {

    private Reader reader;

    private Library library;

    private static int rentId = 1;

    private ArrayList<String> rentalCatalog;

    public Rent(Reader reader, Library library) {
        this.reader = reader;
        this.library = library;
        rentalCatalog = new ArrayList<>();
    }

    public Reader getReader() {
        return reader;
    }
    public Library getLibrary() {
        return library;
    }
    public ArrayList<String> getRentalCatalog() {
        return rentalCatalog;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
    public void setLibrarian(Library library) {
        this.library = library;
    }
    public void setRentalCatalog(ArrayList<String> rentalCatalog) {
        this.rentalCatalog = rentalCatalog;
    }

    public void start(Book book) {
        reader.takeABook(library, book);
        int i = 5 - String.valueOf(rentId).length();
        String rentDescription = "Сделка№";
        for (; i > 0; i--) {
            rentDescription += "0";
        }
        rentDescription += String.valueOf(rentId);
        rentDescription += "_Библиотека:" + this.library.getTitle() + "_" + "Читатель:" + this.reader.getName();
        rentalCatalog.add(rentDescription);
        rentId++;

    }

    public void stop(Book book) {
        reader.returnTheBook(library, book);
        int i = 5 - String.valueOf(rentId).length();
        String rentDescription = "Сделка№";
        for (; i > 0; i--) {
            rentDescription += "0";
        }
        rentDescription += String.valueOf(rentId);
        rentDescription += "_Библиотека:" + this.library.getTitle() + "_" + "Читатель:" + this.reader.getName();
        rentalCatalog.add(rentDescription);
    }
}
