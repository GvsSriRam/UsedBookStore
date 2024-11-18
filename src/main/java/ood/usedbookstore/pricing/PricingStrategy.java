package ood.usedbookstore.pricing;

import ood.usedbookstore.model.BookCondition;
import ood.usedbookstore.model.BookType;

public interface PricingStrategy {
    double calculatePrice(double currentPrice, BookType bookType, BookCondition previousCondition, BookCondition newCondition);
}
