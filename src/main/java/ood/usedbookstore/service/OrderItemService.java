package ood.usedbookstore.service;

import jakarta.transaction.Transactional;
import ood.usedbookstore.dto.TransactionRequest;
import ood.usedbookstore.exceptions.*;
import ood.usedbookstore.model.*;
import ood.usedbookstore.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class OrderItemService implements OrderItemServiceInterface{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private BookCopyInventoryManagement bookCopyInventoryManagement;

    @Override
    public OrderItem createOrderItem(Order order, OrderItemType orderItemType, User employee, Long bookCopyId, double price, Long returnOrderItemId) throws EntityNotFoundException {
        BookCopy bookCopy = bookCopyService.getBookCopyById(bookCopyId);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setOrderItemType(orderItemType);
        orderItem.setEmployee(employee);
        orderItem.setStatus(OrderItemStatus.PENDING);
        orderItem.setBookCopy(bookCopy);
        orderItem.setPrice(price);

        if (returnOrderItemId != null) {
            OrderItem returnOrderItem = getOrderItemById(returnOrderItemId);
            orderItem.setReturnOrderItem(returnOrderItem);
        }

        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem getOrderItemById(Long orderItemId) throws EntityNotFoundException{
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new EntityNotFoundException("Order item with id " + orderItemId + " not found"));
    }

    @Override
    public OrderItem updateOrderItemStatus(OrderItem orderItem, OrderItemStatus orderItemStatus) throws EntityNotFoundException {
        OrderItem existingOrderItem = getOrderItemById(orderItem.getId());
        existingOrderItem.transitionTo(orderItemStatus);
        return orderItemRepository.save(existingOrderItem);
    }

    @Override
    public OrderItem processBuyOrderItem(User user, User employee, TransactionRequest transactionRequest, Order order) throws EntityNotFoundException {
        BookCopy bookCopy = bookCopyService.getBookCopyById(transactionRequest.getBookCopyId());
        bookCopyInventoryManagement.buyBookCopy(bookCopy.getId());
        OrderItem orderItem = createOrderItem(order, transactionRequest.getOrderItemType(), employee, bookCopy.getId(), bookCopy.getPrice(), null);

        Transaction transaction = transactionService.createTransaction(orderItem, user, TransactionType.DEBIT, orderItem.getPrice());
        return updateOrderItemStatus(orderItem, OrderItemStatus.COMPLETED);
    }

    @Override
    public OrderItem getReturnOrderItemByBookCopy(BookCopy bookCopy) throws EntityNotFoundException {
        if (bookCopyService.getBookCopyById(bookCopy.getId()) == null) {
            throw new EntityNotFoundException("Book copy with id " + bookCopy.getId() + " not found");
        }
        return orderItemRepository.findFirstByBookCopyAndOrderItemTypeOrderByIdDesc(bookCopy, OrderItemType.BUY);
    }

    @Override
    public OrderItem processReturnOrderItem(User user, Branch branch, User employee, TransactionRequest transactionRequest, Order order) throws EntityNotFoundException {
        BookCopy bookCopy = bookCopyInventoryManagement.sellBookCopy(transactionRequest.getBookCopyId(), transactionRequest.getIsbn(), transactionRequest.getBookType(), transactionRequest.getBookCondition(), branch);
        double buyBackPrice = bookCopy.getPrice();

        OrderItem orderItem = createOrderItem(order, transactionRequest.getOrderItemType(), employee, bookCopy.getId(), buyBackPrice, getReturnOrderItemByBookCopy(bookCopy).getId());

        Transaction transaction = transactionService.createTransaction(orderItem, user, TransactionType.CREDIT, orderItem.getPrice());

        Inventory inventory = inventoryService.getInventoryByBookCopyId(bookCopy.getId());
        inventory.setBookStatus(BookStatus.IN_STOCK);
        inventory.setDateModified(LocalDate.now());
        inventory.setBranch(branch);
        inventoryService.updateInventory(inventory);
        return updateOrderItemStatus(orderItem, OrderItemStatus.COMPLETED);
    }

    @Override
    public OrderItem processSellOrderItem(User user, Branch branch, User employee, TransactionRequest transactionRequest, Order order) throws EntityNotFoundException {
        BookCopy bookCopy = bookCopyInventoryManagement.sellBookCopy(transactionRequest.getBookCopyId(), transactionRequest.getIsbn(), transactionRequest.getBookType(), transactionRequest.getBookCondition(), branch);
        double buyBackPrice = bookCopy.getPrice();

        OrderItem orderItem = createOrderItem(order, transactionRequest.getOrderItemType(), employee, bookCopy.getId(), buyBackPrice, null);

        Transaction transaction = transactionService.createTransaction(orderItem, user, TransactionType.CREDIT, orderItem.getPrice());

        Inventory inventory = inventoryService.getInventoryByBookCopyId(bookCopy.getId());
        inventory.setBookStatus(BookStatus.IN_STOCK);
        inventory.setDateAdded(LocalDate.now());
        inventory.setDateModified(LocalDate.now());
        inventory.setBranch(branch);
        inventoryService.updateInventory(inventory);
        return updateOrderItemStatus(orderItem, OrderItemStatus.COMPLETED);
    }

    @Transactional
    public OrderItem processOrderItem(User user, Branch branch, User employee, TransactionRequest transactionRequest, Order order) throws OrderItemTypeException, EntityNotFoundException, AuthorizationException {
        transactionRequest.validate();
        OrderItemType orderItemType = transactionRequest.getOrderItemType();

        if (!userService.existsById(user.getId())) {
            throw new EntityNotFoundException("User not found");
        }

        if (!userService.isEmployee(employee.getId())) {
            throw new AuthorizationException("Employee isn't authorized");
        }

        return switch (orderItemType) {
            case BUY -> processBuyOrderItem(user, employee, transactionRequest, order);
            case RETURN -> processReturnOrderItem(user, branch, employee, transactionRequest, order);
            case SELL -> processSellOrderItem(user, branch, employee, transactionRequest, order);
            default -> throw new OrderItemTypeException("Invalid order item type");
        };
    }
}
