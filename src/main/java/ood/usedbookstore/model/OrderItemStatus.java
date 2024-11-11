package ood.usedbookstore.model;

public enum OrderItemStatus {
    PENDING,
    COMPLETED;

    // TODO: Cancelled, refunded, etc

    public boolean isValidTransition(OrderItemStatus target) {
        switch (this) {
            case PENDING:
                return target == COMPLETED;
            default:
                return false;
        }
    }
}
