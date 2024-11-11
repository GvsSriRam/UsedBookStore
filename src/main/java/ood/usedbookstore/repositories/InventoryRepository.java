package ood.usedbookstore.repositories;

import jakarta.validation.constraints.NotNull;
import ood.usedbookstore.model.BookCopy;
import ood.usedbookstore.model.BookStatus;
import ood.usedbookstore.model.Branch;
import ood.usedbookstore.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional <Inventory> findByBookCopy(@NotNull BookCopy bookCopy);

    List<Inventory> findAllByBookStatus(BookStatus bookStatus);

    List<Inventory> findAllByBookStatusAndBranch(BookStatus bookStatus, Branch branch);
}
