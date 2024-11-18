package ood.usedbookstore;

import ood.usedbookstore.pricing.DefaultPricingStrategy;
import ood.usedbookstore.pricing.PricingStrategy;
import ood.usedbookstore.service.BookCopyService;
import ood.usedbookstore.service.OrderItemService;
import ood.usedbookstore.service.OrderService;
import ood.usedbookstore.service.TransactionService;
import ood.usedbookstore.utils.PricingUtils;
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

    @Bean
    public PricingStrategy pricingStrategy() {
        return new DefaultPricingStrategy();
    }

    @Bean
    public PricingUtils pricingUtils(PricingStrategy pricingStrategy) {
        return new PricingUtils(pricingStrategy);
    }
}
