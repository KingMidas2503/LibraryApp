package Service;

public class Main {

    public static void main(String[] args) {

        //Просто прикольное приветствие:
        System.out.println("Welcome, king Midas!");



        //Проработал функциональность: теперь читатель обращается в библиотеку
        // и взаимодействует с ней через библиотекаря. Библиотекарь выдает и принимает книги,
        // используя DAO-классы ReaderDAO и LibraryDAO
        Library library = new Library("The most based library");

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


        Reader ivan = new Reader("Иван IV");
        Reader petya = new Reader("Пётр I");
        Librarian makar = new Librarian("Макар", library);
        Librarian ibrahim = new Librarian("Ибрагим", library);
        Librarian ostap = new Librarian("Остап", library);
        ivan.takeABook(library, godfather);
        ivan.takeABook(library, ilPrincipe);
        ivan.takeABook(library, customersForLife);
        ivan.takeABook(library, designPatterns);
        petya.takeABook(library, philosophyOfJava);
        petya.takeABook(library, godfather);
        petya.takeABook(library, atlasShrugged);
        ivan.returnTheBook(library, godfather);
        ivan.returnTheBook(library, ilPrincipe);
        ivan.returnTheBook(library, atlasShrugged);
        petya.takeABook(library, atlasShrugged);
        petya.returnTheBook(library, atlasShrugged);
        ivan.takeABook(library, atlasShrugged);

    }
}