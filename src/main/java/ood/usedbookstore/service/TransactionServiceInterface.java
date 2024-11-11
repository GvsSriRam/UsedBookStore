package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.OrderItem;
import ood.usedbookstore.model.Transaction;
import ood.usedbookstore.model.TransactionType;
import ood.usedbookstore.model.User;

public interface TransactionServiceInterface {
    Transaction createTransaction(OrderItem orderItem, User user, TransactionType transactionType, double amount) throws EntityNotFoundException;
}
