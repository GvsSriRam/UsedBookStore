package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.exceptions.InventoryException;
import ood.usedbookstore.model.BookCopy;
import ood.usedbookstore.model.Inventory;

import java.util.List;

public interface InventoryServiceInterface {
    Inventory addInventory(Inventory inventory) throws InventoryException, EntityNotFoundException;

    Inventory getInventoryById(Long id) throws EntityNotFoundException;

    Inventory removeInventory(Long inventoryId) throws InventoryException, EntityNotFoundException;

    Inventory updateInventory(Inventory inventory) throws InventoryException, EntityNotFoundException;

    Inventory getInventoryByBookCopyId(Long bookCopyId) throws InventoryException, EntityNotFoundException;

    List<BookCopy> getInStockBookCopies();

    List<BookCopy> getInStockBookCopiesByBranchId(Long branchId) throws EntityNotFoundException;
}
