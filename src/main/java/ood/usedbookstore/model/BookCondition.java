package ood.usedbookstore.model;

public enum BookCondition {
    VERY_GOOD,
    GOOD,
    BAD,
    VERY_BAD,
    ANY,
    ALL;

    public int getConditionLevel() {
        switch (this) {
            case VERY_GOOD: return 3;
            case GOOD: return 2;
            case BAD: return 1;
            case VERY_BAD: return 0;
            default: return -1; // Shouldn't happen
        }
    }

    public static int maxLevel() {
        return VERY_GOOD.getConditionLevel();
    }

    public boolean isApplicableForBookCopy() {
        return this != ANY && this != ALL;
    }

    public boolean isValidTransition (BookCondition target) {
        int currentLevel = this.getConditionLevel();
        int targetLevel = target.getConditionLevel();
        return currentLevel >= targetLevel;
    }
}
