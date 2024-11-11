package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.OrderItem;
import ood.usedbookstore.model.Transaction;
import ood.usedbookstore.model.TransactionType;
import ood.usedbookstore.model.User;
import ood.usedbookstore.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class TransactionService implements TransactionServiceInterface {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(OrderItem orderItem, User user, TransactionType transactionType, double amount) throws EntityNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setOrderItem(orderItem);
        transaction.setUser(user);
        transaction.setDate(LocalDate.now());
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        return transactionRepository.save(transaction);
    }
}
