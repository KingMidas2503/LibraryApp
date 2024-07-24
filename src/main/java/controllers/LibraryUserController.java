package controllers;

import dto.BookDTO;
import dto.LibraryDTO;
import service.LibraryService;

import java.util.ArrayList;

public interface LibraryUserController {

   BookDTO lookAtBook(long libraryId, long bookId);
   //ArrayList<BookDTO> lookAtAllBooks(long libraryId, long bookId);
}
