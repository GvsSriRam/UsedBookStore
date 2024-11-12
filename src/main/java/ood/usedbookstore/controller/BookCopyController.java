package ood.usedbookstore.controller;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.BookCopy;
import ood.usedbookstore.service.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookcopies")
public class BookCopyController {

    @Autowired
    private Facade facade;

    @GetMapping("/inventory")
    public List<BookCopy> getInventory() {
            return facade.getInventory();
    }

    @GetMapping("/instock")
    public List<BookCopy> getInStockInventory() {
        return facade.getInStockBookCopies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopy> getBookCopy(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facade.getBookCopyById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

