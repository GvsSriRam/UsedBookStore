package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.Book;

import java.util.List;

public interface BookServiceInterface {

    Book addBook(Book book) throws EntityNotFoundException;

    Book removeBook(Long bookId) throws EntityNotFoundException;

    Book updateBook(Book book) throws EntityNotFoundException;

    Book getBookById(Long id) throws EntityNotFoundException, EntityNotFoundException;

    Book getBookByISBN(String ISBN) throws EntityNotFoundException;

    List<Book> getAllBooks();

    List<Book> searchBooks(String keyword) throws EntityNotFoundException;

    List<Book> searchBooksByAuthor(String keyword) throws EntityNotFoundException;

    List<Book> searchBooksByAnyParams(Book book) throws EntityNotFoundException;
}
