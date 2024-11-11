package ood.usedbookstore.service;

import ood.usedbookstore.dto.TransactionRequest;
import ood.usedbookstore.exceptions.AuthorizationException;
import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.exceptions.OrderItemTypeException;
import ood.usedbookstore.model.*;
import ood.usedbookstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class OrderService implements OrderServiceInterface {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public Order createOrder(User user, Branch branch, User employee) throws EntityNotFoundException {
        Order order = new Order();
        order.setUser(user);
        order.setBranch(branch);
        order.setEmployee(employee);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(0.0);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) throws EntityNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public Order updateOrder(Order order) throws EntityNotFoundException {
        Order existingOrder = getOrderById(order.getId());

        existingOrder.setOrderItems(order.getOrderItems());
        existingOrder.setBranch(order.getBranch());
        existingOrder.setUser(order.getUser());
        existingOrder.setEmployee(order.getEmployee());
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setTotalPrice(order.getTotalPrice());
        return orderRepository.save(existingOrder);
    }

    @Override
    public Order processTransactionRequests(Order order, List<TransactionRequest> transactionRequests, User user, User employee, Branch branch) throws OrderItemTypeException, EntityNotFoundException, AuthorizationException {
        double totalPrice = order.getTotalPrice();

        for (TransactionRequest transactionRequest : transactionRequests) {
            OrderItem orderItem = orderItemService.processOrderItem(user, branch, employee, transactionRequest, order);
            totalPrice += orderItem.getTransactionAmount();
        }
        order.setTotalPrice(totalPrice);
        return updateOrder(order);
    }

    @Override
    public double computeTotalPrice(Order order) {
        return 0.0; // TODO: Automatically compute the order amount from order items instead of updating the field every time
    }
}
