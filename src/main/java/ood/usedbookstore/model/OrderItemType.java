package ood.usedbookstore.model;

public enum OrderItemType {
    BUY,
    SELL,
    RETURN;

    public TransactionType getTransactionType() {
        return switch (this) {
            case BUY -> TransactionType.DEBIT;
            case SELL, RETURN -> TransactionType.CREDIT;
        };
    }
}
