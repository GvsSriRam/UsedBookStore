package ood.usedbookstore.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ood.usedbookstore.dto.TransactionRequest;
import ood.usedbookstore.exceptions.*;
import ood.usedbookstore.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Facade {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private UserService userService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InventoryService inventoryService;

    // Book management

    @Transactional
    @ReadOnlyProperty
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Transactional
    @ReadOnlyProperty
    public Book getBookById(Long id) throws EntityNotFoundException {
        return bookService.getBookById(id);
    }

    @Transactional
    @ReadOnlyProperty
    public Book getBookByISBN(String isbn) throws EntityNotFoundException {
        return bookService.getBookByISBN(isbn);
    }

    @Transactional
    public Book addBook(Book book, User user) throws AuthorizationException, EntityNotFoundException {
        if (userService.isEmployee(user)) {
            bookService.addBook(book);
            return bookService.getBookByISBN(book.getIsbn());
        }
        throw new AuthorizationException("User with id " + user.getId() + " not authorized");
    }

    @Transactional
    public Book updateBook(Book book, User user) throws AuthorizationException, EntityNotFoundException {
        if (userService.isEmployee(user)) {
            bookService.updateBook(book);
            return bookService.getBookByISBN(book.getIsbn());
        }
        throw new AuthorizationException("User with id " + user.getId() + " not authorized");
    }

    @Transactional
    public Book deleteBook(Book book, User user) throws AuthorizationException, EntityNotFoundException {
        if (userService.isAdmin(user)) {
            bookService.removeBook(book.getId());
            return bookService.getBookByISBN(book.getIsbn());
        }
        throw new AuthorizationException("User with id " + user.getId() + " not authorized");
    }

    // BookCopy Management

    @Transactional
    @ReadOnlyProperty
    public List<BookCopy> getInventory() {
        return bookCopyService.getAllBookCopy();
    }

    @Transactional
    @ReadOnlyProperty
    public List<BookCopy> getInStockBookCopies() {
        return inventoryService.getInStockBookCopies();
    }

    @Transactional
    @ReadOnlyProperty
    public BookCopy getBookCopyById(Long id) throws EntityNotFoundException {
        return bookCopyService.getBookCopyById(id);
    }

    // Order Management

    @Transactional
    public Order placeOrder(String userSUID, Long branchId, String employeeSUID, @NotNull @NotBlank @NotEmpty List<TransactionRequest> transactionRequests) throws OrderProcessingException, EntityNotFoundException, OrderItemTypeException, EmptyOrderException {

        if (transactionRequests == null || transactionRequests.isEmpty()) {
            throw new EmptyOrderException("Empty transaction requests");
        }

        // TODO: If there's any error, none of these should be saved in DB
        User user = userService.getUserBySUID(userSUID);
        Branch branch = branchService.getBranchById(branchId);
        User employee = userService.getUserBySUID(employeeSUID);

        try {
            Order order = orderService.createOrder(user, branch, employee);
            return orderService.processTransactionRequests(order, transactionRequests, user, employee, branch);
        } catch (Exception e) {
            throw new OrderProcessingException("Error while processing the order, rolling back changes.");
        }

    }

}
