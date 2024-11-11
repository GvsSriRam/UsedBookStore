package ood.usedbookstore.repositories;

import ood.usedbookstore.model.BookCopy;
import ood.usedbookstore.model.OrderItem;
import ood.usedbookstore.model.OrderItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findFirstByBookCopyAndOrderItemTypeOrderByIdDesc(BookCopy bookCopy, OrderItemType orderItemType);
}
