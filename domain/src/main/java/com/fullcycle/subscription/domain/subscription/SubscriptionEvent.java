package com.fullcycle.subscription.domain.subscription;

import com.fullcycle.subscription.domain.DomainEvent;
import com.fullcycle.subscription.domain.plan.Plan;
import com.fullcycle.subscription.domain.utils.InstantUtils;

import java.time.Instant;
import java.time.LocalDate;

public sealed interface SubscriptionEvent extends DomainEvent {

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

    record SubscriptionIncomplete(String subscriptionId, String accountId, Long planId, String reason,
                                  LocalDate dueDate,
                                  Instant occurredOn) implements SubscriptionEvent {

        public SubscriptionIncomplete {
            this.assertArgumentNotEmpty(subscriptionId, "'subscriptionId' should not be empty");
            this.assertArgumentNotEmpty(accountId, "'accountId' should not be empty");
            this.assertArgumentNotNull(planId, "'planId' should not be null");
            this.assertArgumentNotEmpty(reason, "'reason' should not be empty");
            this.assertArgumentNotNull(dueDate, "'dueDate' should not be null");
            this.assertArgumentNotNull(occurredOn, "'occurredOn' should not be null");
        }

        public SubscriptionIncomplete(final Subscription anSubscription, final String aReason) {
            this(
                    anSubscription.id().value(),
                    anSubscription.accountId().value(),
                    anSubscription.planId().value(),
                    aReason,
                    anSubscription.dueDate(),
                    InstantUtils.now()
            );
        }
    }


    record SubscriptionRenewed(String subscriptionId, String accountId, Long planId, String transactionId,
                               LocalDate dueDate,
                               String currency,
                               Double amount,
                               Instant renewedAt,
                               Instant occurredOn) implements SubscriptionEvent {

        public SubscriptionRenewed {
            this.assertArgumentNotEmpty(subscriptionId, "'subscriptionId' should not be empty");
            this.assertArgumentNotEmpty(accountId, "'accountId' should not be empty");
            this.assertArgumentNotNull(planId, "'planId' should not be null");
            this.assertArgumentNotEmpty(transactionId, "'transactionId' should not be empty");
            this.assertArgumentNotNull(dueDate, "'dueDate' should not be null");
            this.assertArgumentNotEmpty(currency, "'currency' should not be empty");
            this.assertArgumentNotNull(amount, "'amount' should not be null");
            this.assertArgumentNotNull(renewedAt, "'renewedAt' should not be null");
            this.assertArgumentNotNull(occurredOn, "'occurredOn' should not be null");
        }

        public SubscriptionRenewed(final Subscription anSubscription, final Plan selectedPlan) {
            this(
                    anSubscription.id().value(),
                    anSubscription.accountId().value(),
                    anSubscription.planId().value(),
                    anSubscription.lastTransactionId(),
                    anSubscription.dueDate(),
                    selectedPlan.price().currency().getCurrencyCode(),
                    selectedPlan.price().amount(),
                    anSubscription.lastRenewDate(),
                    InstantUtils.now()
            );
        }
    }

    record SubscriptionCanceled(String subscriptionId, String accountId, Long planId,
                                LocalDate dueDate,
                                Instant occurredOn) implements SubscriptionEvent {

        public SubscriptionCanceled {
            this.assertArgumentNotEmpty(subscriptionId, "'subscriptionId' should not be empty");
            this.assertArgumentNotEmpty(accountId, "'accountId' should not be empty");
            this.assertArgumentNotNull(planId, "'planId' should not be null");
            this.assertArgumentNotNull(dueDate, "'dueDate' should not be null");
            this.assertArgumentNotNull(occurredOn, "'occurredOn' should not be null");
        }

        public SubscriptionCanceled(final Subscription anSubscription) {
            this(
                    anSubscription.id().value(),
                    anSubscription.accountId().value(),
                    anSubscription.planId().value(),
                    anSubscription.dueDate(),
                    InstantUtils.now()
            );
        }
    }
}
