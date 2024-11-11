package ood.usedbookstore.model;

public enum BookStatus {
    IN_STOCK,
    SOLD,
    ANY,
    ALL;

    public boolean isApplicableForBookCopy () {
        return this != ANY && this != ALL;
    }

    public boolean isValidTransition(BookStatus target) {
        switch (this) {
            case IN_STOCK:
                return target == SOLD;
            case SOLD:
                return target == IN_STOCK;
            default:
                return false;
        }
    }
}
