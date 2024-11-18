package ood.usedbookstore.utils;

import ood.usedbookstore.model.BookCondition;
import ood.usedbookstore.model.BookType;
import ood.usedbookstore.pricing.PricingStrategy;

public class PricingUtils {

    private final PricingStrategy pricingStrategy;

    public PricingUtils(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double calculatePrice(double currentPrice, BookType bookType, BookCondition previousCondition, BookCondition newCondition) {
        return pricingStrategy.calculatePrice(currentPrice, bookType, previousCondition, newCondition);
    }
}
