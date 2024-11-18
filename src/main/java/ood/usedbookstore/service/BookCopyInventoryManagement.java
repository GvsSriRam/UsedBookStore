package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.BookCopyAlreadySoldException;
import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.exceptions.InventoryException;
import ood.usedbookstore.model.*;
import ood.usedbookstore.utils.PricingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookCopyInventoryManagement {

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PricingUtils pricingUtils;

    public BookCopy buyBookCopy(Long id) throws InventoryException, EntityNotFoundException, BookCopyAlreadySoldException {
        BookCopy bookCopy = bookCopyService.getBookCopyById(id);

        Inventory inventory = inventoryService.getInventoryByBookCopyId(bookCopy.getId());
        BookStatus bookStatus = inventory.getBookStatus();
        if (bookStatus == BookStatus.SOLD) {
            throw new BookCopyAlreadySoldException("Book Copy with "+id+" is already sold.");
        }
        inventory.setBranch(null);
        inventory.transitionTo(BookStatus.SOLD);
        inventoryService.updateInventory(inventory);
        return bookCopy;
    }

    public BookCopy sellBookCopy(Long bookCopyId, String isbn, BookType booktype, BookCondition bookCondition, Branch branch) throws InventoryException, EntityNotFoundException {
        BookCopy bookCopy;

        if (bookCopyId != null) {
            bookCopy = bookCopyService.getBookCopyById(bookCopyId);
            bookCopy = updateExistingBookCopyInInventory(bookCopy, bookCondition, branch);
        } else {
            bookCopy = createNewBookCopyInInventory(isbn, booktype, bookCondition, branch);
        }
        return bookCopy;
    }

    private BookCopy updateExistingBookCopyInInventory(BookCopy bookCopy, BookCondition bookCondition, Branch branch) throws InventoryException, EntityNotFoundException {
        double sellPrice = pricingUtils.calculatePrice(bookCopy.getPrice(), bookCopy.getBookType(), bookCopy.getBookCondition(), bookCondition);
        bookCopy.updateCondition(bookCondition);
        bookCopy.setPrice(sellPrice);
        bookCopy = bookCopyService.updateBookCopy(bookCopy);

        Inventory inventory = inventoryService.getInventoryByBookCopyId(bookCopy.getId());
        inventory.transitionTo(BookStatus.IN_STOCK);
        inventory.setDateModified(LocalDate.now());
        inventoryService.updateInventory(inventory);
        return bookCopy;
    }

    private BookCopy createNewBookCopyInInventory(String isbn, BookType bookType, BookCondition bookCondition, Branch branch) throws EntityNotFoundException {
        Book book = bookService.getBookByISBN(isbn);
        double sellPrice = pricingUtils.calculatePrice(book.getMRP(), bookType, null, bookCondition);
        BookCopy bookCopy = new BookCopy(book, bookType, bookCondition, sellPrice);
        bookCopy = bookCopyService.addBookCopy(bookCopy);
        inventoryService.addInventory(new Inventory(branch, bookCopy));
        return bookCopy;
    }

}
