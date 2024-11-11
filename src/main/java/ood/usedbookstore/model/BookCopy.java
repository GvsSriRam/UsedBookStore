package ood.usedbookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bookId", referencedColumnName = "Id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private BookType bookType;

    @Column(nullable = false)
    private BookCondition bookCondition;

    @Column(nullable = false)
    @Positive
    private double price;

    // TODO: @Version to handle concurrent requests

    @PrePersist
    @PreUpdate
    private void validate() {
        if (!bookType.isApplicableForBookCopy()) {
            throw new IllegalStateException("BookType "+ bookType +" is not applicable for book copy");
        }
        if (!bookCondition.isApplicableForBookCopy()) {
            throw new IllegalStateException("BookCondition "+ bookCondition +" is not applicable for book copy");
        }
        if (price > book.getMRP()) {
            throw new IllegalStateException("Price cannot be more than MRP");
        }
    }

    public BookCopy(long Id, Book book, BookType bookType, BookCondition bookCondition, double price) {
        super();
        this.Id = Id;
        this.book = book;
        this.bookType = bookType;
        this.bookCondition = bookCondition;
        this.price = price;
    }

    public BookCopy(Book book, BookType bookType, BookCondition bookCondition, double price) {
        super();
        this.book = book;
        this.bookType = bookType;
        this.bookCondition = bookCondition;
        this.price = price;
    }

    public void updateCondition(BookCondition newBookCondition) {
        if (this.bookCondition.isValidTransition(newBookCondition)) {
            this.bookCondition = newBookCondition;
        } else {
            throw new IllegalStateException("Invalid condition transition from " + this.bookCondition + " to " + newBookCondition);
        }
    }
}
