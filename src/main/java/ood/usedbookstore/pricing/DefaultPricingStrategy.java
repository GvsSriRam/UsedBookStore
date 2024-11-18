package ood.usedbookstore.pricing;

import ood.usedbookstore.model.BookCondition;
import ood.usedbookstore.model.BookType;

public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double currentPrice, BookType bookType, BookCondition previousCondition, BookCondition newCondition) {
        int diff;
        if (previousCondition == null) {
            diff = BookCondition.maxLevel() - newCondition.getConditionLevel(); // Assuming you have a maxLevel method
        } else if (newCondition.getConditionLevel() > previousCondition.getConditionLevel()) {
            throw new IllegalArgumentException("Returned book condition can't be better than its previous condition.");
        } else {
            diff = newCondition.getConditionLevel() - previousCondition.getConditionLevel();
        }
        double deprecation = bookType.getDeprecationIncrease() * (diff + 1);
        return currentPrice * (1 - deprecation);
    }
}
