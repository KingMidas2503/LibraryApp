public class Librarian {

    private String name;

    private Library library;

    public Librarian(String name, Library library) {
        this.name = name;
        this.library = library;
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

    public void giveABook(Reader reader) {
        reader.registerInLibrary(library);
        int index = reader.chooseABook(library.availableBooks);
        if (index != -1) {
            Book book = library.availableBooks.get(index);
            reader.takeABook(book);
            library.availableBooks.remove(index);
            library.countOfUsingBooks++;
            System.out.println("Сейчас в библиотеке \"" + library.getTitle() + "\" осталось " + library.availableBooks.size() + " книг в наличии. Всего к этой библиотеке относится " + getLibrary().totalBooks.size() + " книг. Из них " + library.countOfUsingBooks + " находятся в пользовании");
        }
        else {
            System.out.println("В библиотеке \"" + library.getTitle() + "\" не осталось книг");
        }

    }

}
