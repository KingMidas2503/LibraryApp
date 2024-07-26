package dto;

import lombok.Getter;


@Getter
public class RentDTO {

    private long id;
    private long readerId;
    private long bookId;
    private long libraryId;
    private String status;
    String mainString;

    public RentDTO(long id, long readerId, long bookId, long libraryId, boolean isActive) {
        this.id = id;
        this.readerId = readerId;
        this.bookId = bookId;
        this.libraryId = libraryId;
        this.status = isActive ? "активна" : "неактивна";
        mainString = "Аренда №" + id + "; читататель: " + readerId + "; книга: " + bookId + "; библиотека: " + libraryId + "; статус: " + status;
    }


    @Override
    public String toString() {
        return mainString;
    }
}
