package ood.usedbookstore.controller;

import ood.usedbookstore.dto.BookAndUser;
import ood.usedbookstore.exceptions.AuthorizationException;
import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.Book;
import ood.usedbookstore.model.User;
import ood.usedbookstore.service.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private Facade facade;

    @GetMapping("/inventory")
    public List<Book> getInventory() {
        return facade.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        try {
            return facade.getBookById(id);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable String isbn) {
        try {
            return ResponseEntity.ok(facade.getBookByISBN(isbn));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewBook(@RequestBody BookAndUser requestBody) {
        try {
            User user = requestBody.getUser();
            Book book = requestBody.getBook();

            facade.addBook(book, user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to add book");
        } catch (AuthorizationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateBook(@RequestBody BookAndUser requestBody) {
        try {
            User user = requestBody.getUser();
            Book book = requestBody.getBook();

            facade.updateBook(book, user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update book");
        } catch (AuthorizationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteBook(@RequestBody BookAndUser requestBody) {
        try {
            User user = requestBody.getUser();
            Book book = requestBody.getBook();

            facade.deleteBook(book, user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete book");
        } catch (AuthorizationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}