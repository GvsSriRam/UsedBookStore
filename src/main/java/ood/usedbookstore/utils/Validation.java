package ood.usedbookstore.utils;

import ood.usedbookstore.model.User;
import ood.usedbookstore.service.UserService;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

public class Validation {

    public static void validateString(String str, String columnName) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(columnName+" cannot be null or empty.");
        }
    }

    public static void validateISBN(String str, String columnName) {
        // TODO: Consider ISBN-10, ISBN-13 formats from pre-built modules
        validateString(str, columnName);
        if (!str.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException(columnName+" is not a valid ISBN number");
        } else if ( str.length() != 10) {
            throw new IllegalArgumentException(columnName+" is not a valid ISBN number");
        }
    }

    public static void validatePublicationDate(LocalDate publicationDate, String columnName) { // TODO: Move to utils
        if (publicationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(columnName + " cannot be in the future.");
        }
    }

    public static void validateEmployee(User employee) throws AccessDeniedException {
        UserService userService = new UserService();
        if (!userService.isEmployee(employee)) {
            throw new AccessDeniedException("Employee with id " + employee.getId() + " is not authorized");
        }
    }
}
