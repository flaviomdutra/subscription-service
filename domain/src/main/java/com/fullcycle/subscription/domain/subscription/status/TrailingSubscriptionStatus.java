package com.fullcycle.subscription.domain.subscription.status;

import com.fullcycle.subscription.domain.subscription.Subscription;

import static com.fullcycle.subscription.domain.subscription.SubscriptionCommand.ChangeStatus;

public record TrailingSubscriptionStatus(Subscription subscription) implements SubscriptionStatus {

    @Override
    public void trailing() {
        // Do nothing
    }

    @Override
    public void incomplete() {
        this.subscription.execute(new ChangeStatus(new IncompleteSubscriptionStatus(this.subscription)));
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
