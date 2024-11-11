package ood.usedbookstore.model;

public enum TransactionType {
    DEBIT,
    CREDIT;

    public int getTransactionSign() {
        return switch (this) {
            case DEBIT -> 1;
            case CREDIT -> -1;
        };
    }
}
