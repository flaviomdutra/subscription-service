package com.fullcycle.subscription.domain.subscription;

import com.fullcycle.subscription.domain.utils.InstantUtils;

import java.time.Instant;

record SubscriptionCreated(String subscriptionId, String accountId, Long planId,
                           Instant occurredOn) implements SubscriptionEvent {

    public SubscriptionCreated {
        this.assertArgumentNotEmpty(subscriptionId, "'subscriptionId' should not be empty");
        this.assertArgumentNotEmpty(accountId, "'accountId' should not be empty");
        this.assertArgumentNotNull(planId, "'planId' should not be null");
        this.assertArgumentNotNull(occurredOn, "'occurredOn' should not be null");
    }

    public SubscriptionCreated(final Subscription anSubscription) {
        this(
                anSubscription.id().value(),
                anSubscription.accountId().value(),
                anSubscription.planId().value(),
                InstantUtils.now()
        );
    }
}