package ood.usedbookstore.utils;

import ood.usedbookstore.model.BookCondition;
import ood.usedbookstore.model.BookType;

public class PricingUtils {

    public static double calculateBuyBackPrice(double currentPrice, BookType bookType, BookCondition previousBookCondition, BookCondition newBookCondition) {
        int diff;
        if (previousBookCondition == null) {
            diff = BookCondition.maxLevel() - newBookCondition.getConditionLevel();
        } else if (newBookCondition.getConditionLevel() > previousBookCondition.getConditionLevel()) {
            throw new IllegalArgumentException("Returned book condition can't be better than its previous condition.");
        } else {
            diff = newBookCondition.getConditionLevel() - previousBookCondition.getConditionLevel();
        }
        double deprecation = bookType.getDeprecationIncrease() * (diff + 1); // TODO: Recheck - Top priority
        return currentPrice * (1 - deprecation);
    }
}
