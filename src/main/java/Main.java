import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome, king Midas!");

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/habrdb", "habrpguser", "pgpwd4habr");
            System.out.println("Соединение установлено");
        } catch (ClassNotFoundException e) {
            System.out.println("Блин, возникла ошибка : ");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Блин, возникла ошибка : ");
            e.printStackTrace();
        }


        /*
        Library library = new Library("Библиотека имени Льва Толстого");
        Book godfather = new Book("Крестный отец", "Марио Пьюзо");
        Book ilPrincipe = new Book("Государь", "Никколо Макиавелли");
        Book customersForLife = new Book("Клиенты на всю жизнь", "Пол Браун");
        Book designPatterns = new Book("Паттерны проектирования", "Эрих Гамма");
        Book philosophyOfJava = new Book("Философия Java", "Брюс Эккель");
        Book atlasShrugged = new Book("Атлант расправил плечи", "Айн Рэнд");
        library.addBook(godfather);
        library.addBook(ilPrincipe);
        library.addBook(customersForLife);
        library.addBook(designPatterns);
        library.addBook(philosophyOfJava);
        library.addBook(atlasShrugged);

        Librarian librarianTaras = new Librarian("Тарас", library);
        Reader readerVasya = new Reader("Вася");
        Reader readerPetya = new Reader("Петя");
        librarianTaras.giveABook(readerVasya);
        librarianTaras.giveABook(readerVasya);
        librarianTaras.giveABook(readerVasya);
        librarianTaras.giveABook(readerPetya);
        librarianTaras.giveABook(readerPetya);
        librarianTaras.giveABook(readerPetya);
        librarianTaras.giveABook(readerPetya);*/
    }
}