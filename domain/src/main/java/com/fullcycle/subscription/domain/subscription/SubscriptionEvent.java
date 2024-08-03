package com.fullcycle.subscription.domain.subscription;

import com.fullcycle.subscription.domain.DomainEvent;

public sealed interface SubscriptionEvent extends DomainEvent permits SubscriptionCanceled, SubscriptionIncomplete, SubscriptionRenewed, SubscriptionCreated {

    String TYPE = "Subscription";

    String subscriptionId();

    @Override
    default String aggregateId() {
        return subscriptionId();
    }

    @Override
    default String aggregateType() {
        return TYPE;
    }
}
