package controllers;

import dto.BookDTO;
import dto.LibraryDTO;

import java.util.List;


public interface LibraryUserController {

   BookDTO lookAtBook(long libraryId, long bookId);
   List<BookDTO> lookAtAllBooks(long libraryId);
   LibraryDTO getLibraryById(long libraryId);
}
