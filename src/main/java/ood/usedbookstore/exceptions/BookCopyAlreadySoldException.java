package ood.usedbookstore.exceptions;

public class BookCopyAlreadySoldException extends RuntimeException {
    public BookCopyAlreadySoldException(String message) {
        super(message);
    }
}
