package ood.usedbookstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ood.usedbookstore.utils.Validation;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "Id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "branchId", referencedColumnName = "Id")
    private Branch branch;

    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date must be in the past or present")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @NotBlank
    @Positive
    private double totalPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", referencedColumnName = "Id")
    private User employee; // TODO: Validate employee role of a user

    @PrePersist
    @PreUpdate
    private void validate () throws AccessDeniedException {
        Validation.validateEmployee(employee);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OrderItem> orderItems;

    public static class Builder {
        private User user;
        private Branch branch;
        private LocalDate orderDate;
        private double totalPrice;
        private User employee;
        private List<OrderItem> orderItems = new ArrayList<>();

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder branch(Branch branch) {
            this.branch = branch;
            return this;
        }

        public Builder orderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder totalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder employee(User employee) {
            this.employee = employee;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder orderItem(OrderItem orderItem) {
            this.orderItems.add(orderItem);
            return this;
        }

        public Order build() {
            // Perform validation or calculations if needed
            Order order = new Order();
            order.user = this.user;
            order.branch = this.branch;
            order.orderDate = this.orderDate;
            order.totalPrice = this.totalPrice;
            order.employee = this.employee;
            order.orderItems = this.orderItems;
            return order;
        }
    }
}
