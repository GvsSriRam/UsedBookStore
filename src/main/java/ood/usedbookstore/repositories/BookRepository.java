package ood.usedbookstore.repositories;

import ood.usedbookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findById(Long bookId);

    List<Book> findByTitleContainingOrAuthorContainingOrPublisherContainingOrIsbnContaining(String keyword, String keyword1, String keyword2, String keyword3);

    List<Book> findByAuthorContaining(String keyword);

}
