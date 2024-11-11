package ood.usedbookstore;

import ood.usedbookstore.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

    @Bean
    public BookCopyService bookCopyService() {
        return new BookCopyService();
    }

    @Bean
    public OrderService orderService() {
        return new OrderService();
    }

    @Bean
    public OrderItemService orderItemService() {
        return new OrderItemService();
    }

    @Bean
    public TransactionService transactionService() {
        return new TransactionService();
    }

}
