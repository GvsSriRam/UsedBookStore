package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.Book;
import ood.usedbookstore.repositories.BookRepository;
import ood.usedbookstore.repositories.RedisBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BookService")
public class BookService implements BookServiceInterface{

    private final BookRepository bookRepository;

    private final RedisBookRepository redisBookRepository;

    public BookService(BookRepository bookRepository, RedisBookRepository redisBookRepository) {
        this.bookRepository = bookRepository;
        this.redisBookRepository = redisBookRepository;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public Book removeBook(Long bookId) throws EntityNotFoundException {
        Book existingBook = getBookById(bookId);
        bookRepository.delete(existingBook);
        return existingBook;
    }

    @Override
    public Book updateBook(Book book) throws EntityNotFoundException {
        Book existingBook = getBookById(book.getId());

        existingBook.setAuthor(book.getAuthor());
        existingBook.setTitle(book.getTitle());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublicationDate(book.getPublicationDate());
        existingBook.setMRP(book.getMRP());
        return bookRepository.saveAndFlush(existingBook);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooks(String keyword) throws EntityNotFoundException { // TODO: High Priority
        return bookRepository.findByTitleContainingOrAuthorContainingOrPublisherContainingOrIsbnContaining(keyword, keyword, keyword, keyword);
    }

    @Override
    public List<Book> searchBooksByAuthor(String keyword) throws EntityNotFoundException { // TODO: High Priority
        return bookRepository.findByAuthorContaining(keyword);
    }

    @Override
    public List<Book> searchBooksByAnyParams(Book book) throws EntityNotFoundException { // TODO: High Priority
        return List.of();
    }

    @Override
    public Book getBookById(Long id) throws EntityNotFoundException {
        // TODO: If not in cache, get from the database and cache it
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
        redisBookRepository.cacheBook(book);
        return book;
    }

    @Override
    public Book getBookByISBN(String ISBN) throws EntityNotFoundException {
        // TODO: If not in cache, get from the database and cache it
        Book book = bookRepository.findByIsbn(ISBN)
                .orElseThrow(() -> new EntityNotFoundException("Book with ISBN " + ISBN + " not found"));
        redisBookRepository.cacheBook(book);
        return book;
    }
}
