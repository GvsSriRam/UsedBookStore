package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.exceptions.InventoryException;
import ood.usedbookstore.model.BookCopy;
import ood.usedbookstore.model.BookStatus;
import ood.usedbookstore.model.Inventory;
import ood.usedbookstore.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("InventoryService")
public class InventoryService implements InventoryServiceInterface{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BranchService branchService;

    @Autowired
    private BookCopyService bookCopyService;

    @Override
    public Inventory addInventory(Inventory inventory) throws InventoryException, EntityNotFoundException {
        inventory.setBranch(branchService.getBranchById(inventory.getBranch().getId()));

        BookCopy bookCopy = bookCopyService.getBookCopyById(inventory.getBookCopy().getId());

        inventory.setBookCopy(bookCopy);
        return inventoryRepository.saveAndFlush(inventory);
    }

    @Override
    public Inventory getInventoryById(Long id) throws EntityNotFoundException {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory with " + id + " not found"));
    }

    @Override
    public Inventory removeInventory(Long inventoryId) throws InventoryException, EntityNotFoundException {
        Inventory inventory = getInventoryById(inventoryId);
        inventoryRepository.delete(inventory);
        return inventory;
    }

    @Override
    public Inventory updateInventory(Inventory inventory) throws InventoryException, EntityNotFoundException {

        Inventory existingInventory = getInventoryById(inventory.getId());
        existingInventory.setBranch(branchService.getBranchById(inventory.getBranch().getId()));
        existingInventory.setBookCopy(bookCopyService.getBookCopyById(inventory.getBookCopy().getId()));
        existingInventory.setDateAdded(inventory.getDateAdded());
        existingInventory.setDateModified(inventory.getDateModified());
        existingInventory.setBookStatus(inventory.getBookStatus());
        return inventoryRepository.saveAndFlush(existingInventory);
    }

    @Override
    public Inventory getInventoryByBookCopyId(Long bookCopyId) throws InventoryException, EntityNotFoundException {
        BookCopy bookCopy = bookCopyService.getBookCopyById(bookCopyId);
        return inventoryRepository.findByBookCopy(bookCopy)
                .orElseThrow(() -> new InventoryException("Inventory not found for BookCopy with ID " + bookCopyId));
    }

    @Override
    public List<BookCopy> getInStockBookCopies() {
        return inventoryRepository.findAllByBookStatus(BookStatus.IN_STOCK).stream()
                .map(Inventory::getBookCopy)
                .toList();
    }

    @Override
    public List<BookCopy> getInStockBookCopiesByBranchId(Long branchId) throws EntityNotFoundException {
        return inventoryRepository.findAllByBookStatusAndBranch(
                        BookStatus.IN_STOCK, branchService.getBranchById(branchId))
                .stream().map(Inventory::getBookCopy).toList();
    }
}
