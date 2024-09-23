package com.fullcycle.subscription.infrastructure.rest.controllers;

import com.fullcycle.subscription.application.subscription.CancelSubscription;
import com.fullcycle.subscription.application.subscription.CreateSubscription;
import com.fullcycle.subscription.infrastructure.authentication.principal.CodeflixUser;
import com.fullcycle.subscription.infrastructure.rest.SubscriptionRestApi;
import com.fullcycle.subscription.infrastructure.rest.models.req.CreateSubscriptionRequest;
import com.fullcycle.subscription.infrastructure.rest.models.res.CancelSubscriptionResponse;
import com.fullcycle.subscription.infrastructure.rest.models.res.CreateSubscriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class SubscriptionRestController implements SubscriptionRestApi {

    private final CreateSubscription createSubscription;
    private final CancelSubscription cancelSubscription;

    public SubscriptionRestController(final CreateSubscription createSubscription, CancelSubscription cancelSubscription) {
        this.createSubscription = Objects.requireNonNull(createSubscription);
        this.cancelSubscription = Objects.requireNonNull(cancelSubscription);
    }

    @Override
    public ResponseEntity<CreateSubscriptionResponse> createSubscription(final CreateSubscriptionRequest req, final CodeflixUser principal) {
        record CreateSubscriptionInput(Long planId, String accountId) implements CreateSubscription.Input {
        }

        final var res = this.createSubscription.execute(new CreateSubscriptionInput(req.planId(), principal.accountId()), CreateSubscriptionResponse::new);
        return ResponseEntity.created(URI.create("/subscriptions/%s".formatted(res.subscriptionId())))
                .body(res);
    }

    @Override
    public ResponseEntity<CancelSubscriptionResponse> cancelSubscription(final CodeflixUser principal) {
        record CancelSubscriptionInput(String accountId) implements CancelSubscription.Input {
        }
        final var res = this.cancelSubscription.execute(new CancelSubscriptionInput(principal.accountId()), CancelSubscriptionResponse::new);
        return ResponseEntity.ok(res);
    }
}