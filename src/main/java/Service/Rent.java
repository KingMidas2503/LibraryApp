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
/*
name: CI
on:
  push:
    branches:
      - 49385
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v2
      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          distribution: 'adopt'
          java-version: '11'
      - name: Build application
        run: |
          mvn clean install

      - name: Check if build failed
        run: |
          if [ $? -ne 0 ]; then
            echo "Build failed"
            exit 1
          fi
 */
