package ood.usedbookstore.dto;

import lombok.Getter;
import ood.usedbookstore.model.Book;
import ood.usedbookstore.model.User;

@Getter
public class BookAndUser {
    private Book book;
    private User user;
}
