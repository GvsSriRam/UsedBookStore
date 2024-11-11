package ood.usedbookstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId", referencedColumnName = "Id")
    private Branch branch;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookCopyId", referencedColumnName = "Id")
    private BookCopy bookCopy;

    @Column(nullable = false)
    @PastOrPresent(message = "Date added must be in the past or present")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateAdded;

    @Column(nullable = false)
    @PastOrPresent(message = "Date modified must be in the past or present")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @UpdateTimestamp
    private LocalDate dateModified;

    @Column(nullable = false)
    private BookStatus bookStatus;

    public Inventory(Branch branch, BookCopy bookCopy, LocalDate dateAdded, LocalDate dateModified, BookStatus bookStatus) {
        super();
        this.branch = branch;
        this.bookCopy = bookCopy;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
        this.bookStatus = bookStatus;
    }

    public Inventory(Branch branch, BookCopy bookCopy) {
        super();
        this.branch = branch;
        this.bookCopy = bookCopy;
        this.dateAdded = LocalDate.now();
        this.dateModified = LocalDate.now();
        this.bookStatus = BookStatus.IN_STOCK;
    }

    public void transitionTo (BookStatus newBookStatus) {
        if (this.bookStatus.isValidTransition(newBookStatus)) {
            this.bookStatus = newBookStatus;
        } else {
            throw new IllegalStateException("Invalid state transition from " + this.bookStatus + " to " + newBookStatus);
        }
    }
}
