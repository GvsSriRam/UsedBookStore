package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.Book;
import ood.usedbookstore.model.BookCopy;
import ood.usedbookstore.repositories.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BookCopyService")
public class BookCopyService implements BookCopyServiceInterface{

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookService bookService;

    @Override
    public BookCopy addBookCopy(BookCopy bookCopy) throws EntityNotFoundException {
        Book existingBook = bookService.getBookById(bookCopy.getBook().getId());
        bookCopy.setBook(existingBook);
        return bookCopyRepository.saveAndFlush(bookCopy);
    }

    @Override
    public BookCopy removeBookCopy(long bookCopyId) throws EntityNotFoundException {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new EntityNotFoundException("Book Copy with "+bookCopyId+" not found."));
        bookCopyRepository.deleteById(bookCopyId);
        return bookCopy;
    }

    @Override
    public BookCopy updateBookCopy(BookCopy bookCopy) throws EntityNotFoundException {
        BookCopy existingBookCopy = bookCopyRepository.findById(bookCopy.getId())
                .orElseThrow(() -> new EntityNotFoundException("Book Copy with "+bookCopy.getId()+" not found."));

        existingBookCopy.setBook(bookCopy.getBook());
        existingBookCopy.setBookCondition(bookCopy.getBookCondition());
        existingBookCopy.setPrice(bookCopy.getPrice());
        existingBookCopy.setBookType(bookCopy.getBookType());
        return bookCopyRepository.saveAndFlush(existingBookCopy);
    }

    @Override
    public BookCopy getBookCopyById(Long id) throws EntityNotFoundException {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book Copy with "+id+" not found."));
    }

    @Override
    public List<BookCopy> getAllBookCopy() {
        return bookCopyRepository.findAll();
    }

    @Override
    public List<BookCopy> searchBookCopy(String keyword) throws EntityNotFoundException { // TODO: Make a search engine for any generic search keywords. Should be able to match against any field
        return List.of(); // TODO: High Priority
    }

    @Override
    public List<BookCopy> searchBookCopyByAnyParams(BookCopy bookCopy) throws EntityNotFoundException { // TODO: Make a search engine for given fields like with filter options
        return List.of(); // TODO: High Priority
    }
}
