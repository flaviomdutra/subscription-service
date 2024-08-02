package com.fullcycle.subscription.domain.subscription.status;

import com.fullcycle.subscription.domain.exceptions.DomainException;
import com.fullcycle.subscription.domain.subscription.Subscription;

public record CanceledSubscriptionStatus(Subscription subscription) implements SubscriptionStatus {

    @Override
    public void trialing() {
        throw DomainException.with("Subscription with status canceled can't transit to trialing");
    }

    @Override
    public void incomplete() {
        throw DomainException.with("Subscription with status canceled can't transit to incomplete");
    }

    @Override
    public void active() {
        throw DomainException.with("Subscription with status canceled can't transit to active");
    }

    @Override
    public void cancel() {
        // Do nothing
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(getClass());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return value();
    }
}
