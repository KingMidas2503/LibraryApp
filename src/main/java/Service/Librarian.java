package Service;

public class Librarian {

    private String name;

    private Library library;

    public Librarian(String name, Library library) {
        this.name = name;
        this.library = library;
        library.workers.add(this);
    }

    public String getName() {
        return name;
    }
    public Library getLibrary() {
        return library;
    }
    public void setLibrary(Library library) {
        this.library = library;
    }

    public void giveABook(Reader reader, int bookId) {
        reader.registerInLibrary(library);

        if (!library.availableBooks.isEmpty() && library.availableBooks.containsKey(bookId)) {
            reader.takeABook(library.availableBooks, bookId);
            library.availableBooks.remove(bookId);
            library.countOfUsingBooks++;
            System.out.println("Сейчас в библиотеке \"" + library.getTitle() + "\" осталось " + library.availableBooks.size() + " книг в наличии. Всего к этой библиотеке относится " + getLibrary().totalBooks.size() + " книг. Из них " + library.countOfUsingBooks + " находятся в пользовании");
        }
        else if (library.availableBooks.isEmpty()) {
            System.out.println("В библиотеке \"" + library.getTitle() + "\" не осталось книг. К сожалению, читатель " + reader.getName() + " в этот раз не сможет ничего взять.");
        } else {
            System.out.println("Читатель " + reader.getName() + " попытался взять книгу с несуществующим id. В библиотеке \"" + library.getTitle() + "\" нет такой книги");
        }

    }

    public void acceptTheBook(Reader reader, int bookId) {
        reader.registerInLibrary(library);

        if (!reader.booksInUse.isEmpty() && reader.booksInUse.containsKey(bookId)) {
            reader.returnTheBook(library.availableBooks, bookId);
            library.countOfUsingBooks--;
            System.out.println("Сейчас в библиотеке \"" + library.getTitle() + "\" находится в наличии " + library.availableBooks.size() + " книг. Всего к этой библиотеке относится " + getLibrary().totalBooks.size() + " книг. Из них " + library.countOfUsingBooks + " находятся в пользовании");
        }
        else {
            System.out.println("У читателя " + reader.getName() + " нет такой книги. Человек не может вернуть книгу, которой у него нет.");
        }

    }

}
