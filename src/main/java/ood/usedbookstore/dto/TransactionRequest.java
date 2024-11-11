package ood.usedbookstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ood.usedbookstore.model.BookCondition;
import ood.usedbookstore.model.BookType;
import ood.usedbookstore.model.OrderItemType;

@Getter
@Setter
public class TransactionRequest {
    private Long bookCopyId;
    private String isbn; // Only applicable for selling, can't use it if bookCopyId is present
    private @NotNull OrderItemType orderItemType; // BUY or RETURN or SELL
    private BookCondition bookCondition; // Only applicable for selling
    private BookType bookType; // Only applicable for selling

    public void validate() {
        switch (orderItemType) {
            case BUY:
                if (bookCopyId == null) {
                    throw new IllegalArgumentException("Book copy id is required");
                }
                if (isbn != null || bookCondition != null || bookType != null) {
                    throw new IllegalArgumentException("ISBN, bookCondition, bookType are not applicable");
                }
                break;
            case RETURN:
                if (bookCopyId == null) {
                    throw new IllegalArgumentException("Book copy id is required");
                }
                if (isbn != null) {
                    throw new IllegalArgumentException("ISBN are not applicable");
                }
                if (bookCondition == null) {
                    throw new IllegalArgumentException("Book condition is required");
                }
                if (bookType != null) {
                    throw new IllegalArgumentException("Book type is not applicable");
                }
                break;
            case SELL:
                if (isbn == null || bookCondition == null || bookType == null) {
                    throw new IllegalArgumentException("ISBN, bookCondition, bookType are required");
                }
                if (bookCopyId != null) {
                    throw new IllegalArgumentException("Book copy id is not applicable");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid order item type");
        }
    }
}
