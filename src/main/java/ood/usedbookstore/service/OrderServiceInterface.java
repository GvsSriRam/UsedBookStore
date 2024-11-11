package ood.usedbookstore.service;

import ood.usedbookstore.dto.TransactionRequest;
import ood.usedbookstore.exceptions.*;
import ood.usedbookstore.model.Branch;
import ood.usedbookstore.model.Order;
import ood.usedbookstore.model.User;

import java.util.List;

public interface OrderServiceInterface {
    Order createOrder(User user, Branch branch, User employee) throws EntityNotFoundException;

    Order getOrderById(Long id) throws EntityNotFoundException;

    Order updateOrder(Order order) throws EntityNotFoundException;

    Order processTransactionRequests(Order order, List<TransactionRequest> transactionRequests, User user, User employee, Branch branch) throws OrderItemTypeException, EntityNotFoundException, AuthorizationException;

    double computeTotalPrice(Order order);
}
