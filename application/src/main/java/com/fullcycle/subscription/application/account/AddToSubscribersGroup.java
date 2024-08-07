package com.fullcycle.subscription.application.account;

import com.fullcycle.subscription.application.UseCase;
import com.fullcycle.subscription.domain.subscription.SubscriptionId;

public abstract class AddToSubscribersGroup extends UseCase<AddToSubscribersGroup.Input, AddToSubscribersGroup.Output> {

    public interface Input {
        String accountId();

        String groupId();

        String subscriptionId();
    }

    public interface Output {
        SubscriptionId subscriptionId();
    }
}
