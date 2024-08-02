package com.fullcycle.subscription.domain.subscription.status;

import com.fullcycle.subscription.domain.exceptions.DomainException;
import com.fullcycle.subscription.domain.subscription.Subscription;

import static com.fullcycle.subscription.domain.subscription.SubscriptionCommand.ChangeStatus;

public final class ActiveSubscriptionStatus extends AbstractSubscriptionStatus {
    private final Subscription subscription;

    public ActiveSubscriptionStatus(final Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void trialing() {
        throw DomainException.with("Subscription with status active can't transit to trialing");
    }

    @Override
    public void incomplete() {
        this.subscription.execute(new ChangeStatus(INCOMPLETE));
    }

    @Override
    public void cancel() {
        this.subscription.execute(new ChangeStatus(CANCELED));
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
