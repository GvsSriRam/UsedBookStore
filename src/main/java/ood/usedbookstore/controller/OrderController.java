package ood.usedbookstore.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ood.usedbookstore.dto.OrderOverallStatus;
import ood.usedbookstore.dto.OrderResponse;
import ood.usedbookstore.dto.TransactionRequest;
import ood.usedbookstore.exceptions.EmptyOrderException;
import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.exceptions.OrderProcessingException;
import ood.usedbookstore.model.Order;
import ood.usedbookstore.service.Facade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final Facade facade;

    public OrderController(Facade facade) {
        this.facade = facade;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestParam @NotNull @NotBlank @NotEmpty String userSUID,
            @RequestParam @NotNull @NotBlank @NotEmpty Long branchId,
            @RequestParam @NotNull @NotBlank @NotEmpty String employeeSUID,
            @RequestBody @NotNull List<TransactionRequest> transactionRequests) {

        try {
            Order order = facade.placeOrder(userSUID, branchId, employeeSUID, transactionRequests);
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderOverallStatus(OrderOverallStatus.SUCCESS);
            orderResponse.setAmount(order.getTotalPrice());
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } catch (EmptyOrderException | OrderProcessingException | EntityNotFoundException e) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderOverallStatus(OrderOverallStatus.FAILED);
            return new ResponseEntity<>(orderResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderOverallStatus(OrderOverallStatus.FAILED);
            return new ResponseEntity<>(orderResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}