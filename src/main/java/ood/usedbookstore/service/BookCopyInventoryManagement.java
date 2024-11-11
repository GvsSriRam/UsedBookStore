package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.*;
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
        double sellPrice;
        BookCopy bookCopy;
        Inventory inventory;

        if (bookCopyId != null) {
            bookCopy = bookCopyService.getBookCopyById(bookCopyId);
            sellPrice = PricingUtils.calculateBuyBackPrice(bookCopy.getPrice(), bookCopy.getBookType(), bookCopy.getBookCondition(), bookCondition);
            bookCopy.updateCondition(bookCondition);
            bookCopy.setPrice(sellPrice);

            inventory = inventoryService.getInventoryByBookCopyId(bookCopyId);
            inventory.transitionTo(BookStatus.IN_STOCK);
            inventory.setDateModified(LocalDate.now());
            inventoryService.updateInventory(inventory);
        } else {
            Book book = bookService.getBookByISBN(isbn);
            sellPrice = PricingUtils.calculateBuyBackPrice(book.getMRP(), booktype, null, bookCondition);
            bookCopy = new BookCopy(book, booktype, bookCondition, sellPrice);
            bookCopy = bookCopyService.addBookCopy(bookCopy);
            inventoryService.addInventory(new Inventory(branch, bookCopy));
        }
        return bookCopy;
    }
}
