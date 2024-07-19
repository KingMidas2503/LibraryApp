package models;

import jakarta.persistence.*;

@Entity
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "AUTHOR", nullable = false)
    private String author;

    public BookModel() {}

    public BookModel(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @ManyToOne
    @JoinColumn(name = "LIBRARY_MODEL_ID")
    private LibraryModel libraryModel;

    @ManyToOne
    @JoinColumn(name = "READER_MODEL_ID")
    private ReaderModel readerModel;

}
