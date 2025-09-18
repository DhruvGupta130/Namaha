package com.trulydesignfirm.namaha.constant;

public enum SubscriptionStatus {
    ACTIVE,
    PAUSED,
    CANCELLED,
    EXPIRED;

    public boolean isUpdatable() {
        return this == ACTIVE || this == PAUSED;
    }
}
