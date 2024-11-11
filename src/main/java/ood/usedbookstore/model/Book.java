package ood.usedbookstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;

import static ood.usedbookstore.utils.Validation.*;

@Setter
@Getter
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column
    private String edition;

    @Column(nullable = false)
    @NotBlank
    private String author;

    @Column(nullable = false, length = 10, unique = true)
    @NotBlank
    private String isbn;

    @Column()
    private String publisher;

    @Column(nullable = false)
    @PastOrPresent
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @Column(nullable = false)
    @Positive
    private double MRP;

    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<BookCopy> bookCopies;

    @PrePersist
    @PreUpdate
    private void validate() {
        validatePublicationDate(publicationDate, "Publication Date");
        validateString(this.title, "Title");
        validateString(this.author, "Author");
        validateString(this.publisher, "Publisher");
        validateISBN(this.isbn, "ISBN");
    }

    public Book(long Id, String title, String author, String isbn, String publisher, LocalDate publicationDate, double MRP, List<BookCopy> bookCopies) {
        super();
        this.Id = Id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.MRP = MRP;
        this.bookCopies = bookCopies;
    }
}
