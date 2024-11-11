package ood.usedbookstore.model;

import lombok.Getter;

@Getter
public enum BookType {
    HARDCOVER(0.1),
    PAPERBACK(0.15),
    ANY(0.0),
    ALL(0.0);

    private final double deprecationIncrease;

    BookType(double deprecationIncrease) {
        this.deprecationIncrease = deprecationIncrease;
    }

    public boolean isApplicableForBookCopy () {
        return this != ANY && this != ALL;
    }
}
