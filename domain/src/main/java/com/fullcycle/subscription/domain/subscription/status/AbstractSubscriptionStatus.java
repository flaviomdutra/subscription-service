package com.fullcycle.subscription.domain.subscription.status;

public sealed abstract class AbstractSubscriptionStatus implements SubscriptionStatus permits ActiveSubscriptionStatus {

    @Override
    public void trialing() {

    }

    @Override
    public void incomplete() {

    }

    @Override
    public void active() {

    }

    @Override
    public void cancel() {

    }
}
