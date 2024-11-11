package ood.usedbookstore.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.utils.Validation;

import java.nio.file.AccessDeniedException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId", referencedColumnName = "Id", nullable = false)
    private Order order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookCopyId", referencedColumnName = "Id")
    private BookCopy bookCopy;

    @NotNull
    @NotBlank
    @NotEmpty
    @Positive
    private double price;

    @NotNull
    @NotBlank
    @NotEmpty
    private OrderItemType orderItemType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", referencedColumnName = "Id")
    private User employee;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "returnOrderItemId", referencedColumnName = "Id")
    private OrderItem returnOrderItem;

    @Column
    private OrderItemStatus status;

    @PrePersist
    @PreUpdate
    private void validate() throws AccessDeniedException, EntityNotFoundException {
        Validation.validateEmployee(employee);

        // return order item id shouldn't be same as the same order item id
        // leads to infinite loop
        if (returnOrderItem != null && returnOrderItem.getId().equals(Id)) {
            throw new EntityNotFoundException("Return order item shouldn't be same as the current order item");
        }
    }

    public double getTransactionAmount() {
        return orderItemType.getTransactionType().getTransactionSign() * price;
    }

    public void transitionTo(OrderItemStatus newStatus) {
        if (this.status.isValidTransition(newStatus)) {
            this.status = newStatus;
        } else {
            throw new IllegalStateException("Invalid order item status transition from " + this.status + " to " + newStatus);
        }
    }
}
