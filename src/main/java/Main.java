import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;


public class Main {
    public static void main(String[] args) {

        //Просто прикольное приветствие:
        System.out.println("Welcome, king Midas!");

        //Это я подключился к БД:
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


        //Это пример работы программы:
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

        Rent rent = new Rent("Вася", "Тарас", library);
        rent.startARent(0);
        rent.startARent(1);
        rent.startARent(2);
        rent.startARent(3);
        rent.stopTheRent(2);
        rent.startARent(4);
        rent.startARent(7);
        rent.stopTheRent(3);

        for (int i = 0; i < rent.getRentalCatalog().size(); i++) {
            System.out.println(rent.getRentalCatalog().get(i));
        }

    }
}