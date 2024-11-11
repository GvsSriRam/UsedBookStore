package ood.usedbookstore.service;

import jakarta.annotation.PostConstruct;
import ood.usedbookstore.model.*;
import ood.usedbookstore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setRoles(Set.of(Role.ADMIN));
            user.setEmail("admin@gmail.com");
            user.setSuid("1111111111");
            userRepository.save(user);

            User user2 = new User();
            user2.setRoles(Set.of(Role.FULL_TIME_EMPLOYEE));
            user2.setEmail("ftime@gmail.com");
            user2.setSuid("2222222222");
            userRepository.save(user2);

            User user3 = new User();
            user3.setRoles(Set.of(Role.PART_TIME_EMPLOYEE));
            user3.setEmail("parttime@gmail.com");
            user3.setSuid("3333333333");
            userRepository.save(user3);

            User user4 = new User();
            user4.setRoles(Set.of(Role.STUDENT));
            user4.setEmail("student@gmail.com");
            user4.setSuid("4444444444");
            userRepository.save(user4);
        }

        if (branchRepository.count() == 0) {
            Branch branch = new Branch();
            branch.setName("Branch 1");
            branch.setPhoneNumber("123456789");
            branch.setEmail("branch1@gmail.com");
            branch.setAddressLine1("address 1");
            branch.setAddressLine2("address 2");
            branch.setCity("city");
            branch.setState(State.ALABAMA);
            branch.setZip("12345");
            branchRepository.save(branch);

            Branch branch2 = new Branch();
            branch2.setName("Branch 2");
            branch2.setPhoneNumber("123456789");
            branch2.setEmail("branch2@gmail.com");
            branch2.setAddressLine1("address 1");
            branch2.setAddressLine2("address 2");
            branch2.setCity("city");
            branch2.setState(State.ARIZONA);
            branch2.setZip("12345");
            branchRepository.save(branch2);
        }

        if (bookRepository.count() == 0) {
            Book book = new Book();
            book.setTitle("Book 1");
            book.setAuthor("John Doe");
            book.setIsbn("1111111111");
            book.setPublisher("p1");
            book.setPublicationDate(LocalDate.of(2000, 01, 01));
            book.setMRP(150);
            bookRepository.save(book);

            Book book2 = new Book();
            book2.setTitle("Book 2");
            book2.setAuthor("John Doe");
            book2.setIsbn("2222222222");
            book2.setPublisher("p2");
            book2.setPublicationDate(LocalDate.of(2000, 02, 01));
            book2.setMRP(150);
            bookRepository.save(book2);
        }

        if (bookCopyRepository.count() == 0) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBook(bookRepository.findAll().get(0));
            bookCopy.setBookType(BookType.PAPERBACK);
            bookCopy.setBookCondition(BookCondition.BAD);
            bookCopy.setPrice(90);
            bookCopyRepository.save(bookCopy);

            BookCopy bookCopy2 = new BookCopy();
            bookCopy2.setBook(bookRepository.findAll().get(0));
            bookCopy2.setBookType(BookType.PAPERBACK);
            bookCopy2.setBookCondition(BookCondition.GOOD);
            bookCopy2.setPrice(80);
            bookCopyRepository.save(bookCopy2);

            BookCopy bookCopy3 = new BookCopy();
            bookCopy3.setBook(bookRepository.findAll().get(1));
            bookCopy3.setBookType(BookType.HARDCOVER);
            bookCopy3.setBookCondition(BookCondition.VERY_GOOD);
            bookCopy3.setPrice(100);
            bookCopyRepository.save(bookCopy3);

            BookCopy bookCopy4 = new BookCopy();
            bookCopy4.setBook(bookRepository.findAll().get(1));
            bookCopy4.setBookType(BookType.HARDCOVER);
            bookCopy4.setBookCondition(BookCondition.VERY_GOOD);
            bookCopy4.setPrice(80);
            bookCopyRepository.save(bookCopy4);
        }

        if (inventoryRepository.count() == 0) {
            for (BookCopy bookCopy : bookCopyRepository.findAll()) {
                Inventory inventory = new Inventory(branchRepository.findAll().get(0), bookCopy);
                inventoryRepository.save(inventory);
            }
        }
    }
}
