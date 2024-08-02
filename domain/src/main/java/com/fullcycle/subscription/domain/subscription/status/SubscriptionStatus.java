package com.fullcycle.subscription.domain.subscription.status;

import com.fullcycle.subscription.domain.exceptions.DomainException;
import com.fullcycle.subscription.domain.subscription.Subscription;

public sealed interface SubscriptionStatus permits
        AbstractSubscriptionStatus,
        CanceledSubscriptionStatus,
        IncompleteSubscriptionStatus,
        TrialingSubscriptionStatus {

    String TRIALING = "trialing";
    String INCOMPLETE = "incomplete";
    String ACTIVE = "active";
    String CANCELED = "canceled";

    static SubscriptionStatus create(final String status, final Subscription aSubscription) {
        if (aSubscription == null) {
            throw DomainException.with("'subscription' should not be null");
        }

        if (status == null) {
            throw DomainException.with("'status' should not be null");
        }

        return switch (status) {
            case ACTIVE -> new ActiveSubscriptionStatus(aSubscription);
            case CANCELED -> new CanceledSubscriptionStatus(aSubscription);
            case INCOMPLETE -> new IncompleteSubscriptionStatus(aSubscription);
            case TRIALING -> new TrialingSubscriptionStatus(aSubscription);
            default -> throw DomainException.with("Invalid status: %s".formatted(status));
        };
    }

    void trialing();

    void incomplete();

    void active();

    void cancel();

    default String value() {
        return switch (this) {
            case ActiveSubscriptionStatus s -> ACTIVE;
            case CanceledSubscriptionStatus s -> CANCELED;
            case IncompleteSubscriptionStatus s -> INCOMPLETE;
            case TrialingSubscriptionStatus s -> TRIALING;
        };
    }
}
