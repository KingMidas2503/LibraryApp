package Service;

public class Main {

    public static void main(String[] args) {

        //Просто прикольное приветствие:
        System.out.println("Welcome, king Midas!");

        System.out.printf("");

        //Проработал функциональность: теперь читатель обращается в библеотеку
        // и взаимодействует с ней через библиотекаря. Библиотекарь выдает и принимает книги,
        // используя DAO-классы ReaderDAO и LibraryDAO
        Library library = new Library("Библиотека имени Ленина");

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

        //Код ниже пока закомментарен, позднее он будет использоваться для проверки работы других DAO-классов
        Reader ivan = new Reader("Иван");
        Librarian makar = new Librarian("Макар", library);
        Librarian ibrahim = new Librarian("Ибрагим", library);
        Librarian ostap = new Librarian("Остап", library);
        Rent rent = new Rent(ivan, library);
        rent.start(godfather);
        rent.start(ilPrincipe);
        rent.start(customersForLife);
        Reader taras = new Reader("Пётр");
        Rent newRent = new Rent(taras, library);
        newRent.start(godfather);
        rent.start(philosophyOfJava);
        rent.start(atlasShrugged);
        rent.start(designPatterns);
        rent.stop(ilPrincipe);

        for (int i = 0; i < rent.getRentalCatalog().size(); i++) {
            System.out.println(rent.getRentalCatalog().get(i));
        }

    }
}