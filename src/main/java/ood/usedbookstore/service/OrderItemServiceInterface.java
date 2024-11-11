package ood.usedbookstore.service;

import ood.usedbookstore.dto.TransactionRequest;
import ood.usedbookstore.exceptions.*;
import ood.usedbookstore.model.*;

public interface OrderItemServiceInterface {
    OrderItem createOrderItem(Order order, OrderItemType transactionType, User employee, Long bookCopyId, double price, Long returnOrderItemId) throws EntityNotFoundException;

    OrderItem getOrderItemById(Long orderItemId) throws EntityNotFoundException;
    OrderItem updateOrderItemStatus(OrderItem orderItem, OrderItemStatus orderItemStatus) throws EntityNotFoundException;

    OrderItem processBuyOrderItem(User user, User employee, TransactionRequest transactionRequest, Order order) throws EntityNotFoundException;

    OrderItem getReturnOrderItemByBookCopy(BookCopy bookCopy) throws EntityNotFoundException;

    OrderItem processReturnOrderItem(User user, Branch branch, User employee, TransactionRequest transactionRequest, Order order) throws EntityNotFoundException;
    OrderItem processSellOrderItem(User user, Branch branch, User employee, TransactionRequest transactionRequest, Order order) throws EntityNotFoundException;
}
