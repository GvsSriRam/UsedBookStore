package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.BookCopy;

import java.util.List;

public interface BookCopyServiceInterface {

    BookCopy addBookCopy(BookCopy bookCopy) throws EntityNotFoundException;

    BookCopy removeBookCopy(long bookCopyId) throws EntityNotFoundException;

    BookCopy updateBookCopy(BookCopy bookCopy) throws EntityNotFoundException;

    BookCopy getBookCopyById(Long id) throws EntityNotFoundException;

    List<BookCopy> getAllBookCopy() throws EntityNotFoundException;

    List<BookCopy> searchBookCopy(String keyword) throws EntityNotFoundException;

    List<BookCopy> searchBookCopyByAnyParams(BookCopy bookCopy) throws EntityNotFoundException;
}
