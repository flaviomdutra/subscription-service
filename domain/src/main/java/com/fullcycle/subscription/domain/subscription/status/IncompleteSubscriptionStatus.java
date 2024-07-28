package com.fullcycle.subscription.domain.subscription.status;

import com.fullcycle.subscription.domain.exceptions.DomainException;
import com.fullcycle.subscription.domain.subscription.Subscription;

import static com.fullcycle.subscription.domain.subscription.SubscriptionCommand.ChangeStatus;

public record IncompleteSubscriptionStatus(Subscription subscription) implements SubscriptionStatus {

    @Override
    public void trailing() {
        throw DomainException.with("Subscription with status incomplete can't transit to trailing");
    }

    @Override
    public void incomplete() {
        // Do nothing
    }

    @Override
    public void active() {
        this.subscription.execute(new ChangeStatus(new ActiveSubscriptionStatus(this.subscription)));
    }

    @Override
    public void cancel() {
        this.subscription.execute(new ChangeStatus(new CanceledSubscriptionStatus(this.subscription)));
    }
}
