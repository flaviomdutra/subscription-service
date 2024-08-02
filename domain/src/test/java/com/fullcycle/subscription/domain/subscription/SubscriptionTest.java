package com.fullcycle.subscription.domain.subscription;

import com.fullcycle.subscription.domain.Fixture;
import com.fullcycle.subscription.domain.account.AccountId;
import com.fullcycle.subscription.domain.plan.PlanId;
import com.fullcycle.subscription.domain.subscription.status.SubscriptionStatus;
import com.fullcycle.subscription.domain.utils.InstantUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.fullcycle.subscription.domain.subscription.SubscriptionCommand.*;

public class SubscriptionTest {

    @Test
    public void givenValidParams_whenCallsNewSubscription_shouldInstantiate() {
        // given
        var expectedId = new SubscriptionId("SUB123");
        var expectedVersion = 0;
        var expectedAccountId = new AccountId("ACC123");
        var expectPlan = Fixture.Plans.plus();
        var expectedStatus = SubscriptionStatus.TRIALING;
        var expectedDueDate = LocalDate.now().plusMonths(1);
        Instant expectedLastRenewDate = null;
        String expectedLastTransactionId = null;
        var expectedEvents = 1;

        // when
        var actualSubscription = Subscription.newSubscription(expectedId, expectedAccountId, expectPlan);
        // then
        Assertions.assertNotNull(actualSubscription);
        Assertions.assertEquals(expectedId, actualSubscription.id());
        Assertions.assertEquals(expectedVersion, actualSubscription.version());
        Assertions.assertEquals(expectedAccountId, actualSubscription.accountId());
        Assertions.assertEquals(expectPlan.id(), actualSubscription.planId());
        Assertions.assertEquals(expectedDueDate, actualSubscription.dueDate());
        Assertions.assertEquals(expectedStatus, actualSubscription.status().value());
        Assertions.assertEquals(expectedLastRenewDate, actualSubscription.lastRenewDate());
        Assertions.assertEquals(expectedLastTransactionId, actualSubscription.lastTransactionId());
        Assertions.assertNotNull(actualSubscription.createdAt());
        Assertions.assertNotNull(actualSubscription.updatedAt());

        Assertions.assertEquals(expectedEvents, actualSubscription.domainEvents().size());
        Assertions.assertInstanceOf(SubscriptionEvent.SubscriptionCreated.class, actualSubscription.domainEvents().getFirst());
    }

    @Test
    public void givenValidParams_whenCallsWith_shouldInstantiate() {
        // given
        var expectedId = new SubscriptionId("SUB123");
        var expectedVersion = 0;
        var expectedAccountId = new AccountId("ACC123");
        var expectedPlanId = new PlanId(123L);
        var expectedStatus = SubscriptionStatus.TRIALING;
        var expectedDueDate = LocalDate.now().plusMonths(1);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedLastRenewDate = InstantUtils.now().minus(7, ChronoUnit.DAYS);
        var expectedLastTransactionId = UUID.randomUUID().toString();

        // when
        var actualSubscription =
                Subscription.with(
                        expectedId,
                        expectedVersion,
                        expectedAccountId,
                        expectedPlanId,
                        expectedDueDate,
                        expectedStatus,
                        expectedLastRenewDate,
                        expectedLastTransactionId,
                        expectedCreatedAt,
                        expectedUpdatedAt
                );
        // then
        Assertions.assertNotNull(actualSubscription);
        Assertions.assertEquals(expectedId, actualSubscription.id());
        Assertions.assertEquals(expectedVersion, actualSubscription.version());
        Assertions.assertEquals(expectedAccountId, actualSubscription.accountId());
        Assertions.assertEquals(expectedPlanId, actualSubscription.planId());
        Assertions.assertEquals(expectedDueDate, actualSubscription.dueDate());
        Assertions.assertEquals(expectedStatus, actualSubscription.status().value());
        Assertions.assertEquals(expectedLastRenewDate, actualSubscription.lastRenewDate());
        Assertions.assertEquals(expectedLastTransactionId, actualSubscription.lastTransactionId());
        Assertions.assertEquals(expectedCreatedAt, actualSubscription.createdAt());
        Assertions.assertEquals(expectedUpdatedAt, actualSubscription.updatedAt());

        Assertions.assertTrue(actualSubscription.domainEvents().isEmpty());
    }

    @Test
    public void givenTrialSubscription_whenExecuteIncompleteCommand_shouldTransitToIncompleteState() {
        // given
        var expectedId = new SubscriptionId("SUB123");
        var expectedVersion = 0;
        var expectedAccountId = new AccountId("ACC123");
        var expectedPlanId = new PlanId(123L);
        var expectedStatus = SubscriptionStatus.INCOMPLETE;
        var expectedDueDate = LocalDate.now();
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        Instant expectedLastRenewDate = null;
        var expectedLastTransactionId = UUID.randomUUID().toString();
        var expectedReason = "Fail to charge credit card";
        var expectedEvents = 1;

        var actualSubscription =
                Subscription.with(
                        expectedId,
                        expectedVersion,
                        expectedAccountId,
                        expectedPlanId,
                        expectedDueDate,
                        SubscriptionStatus.TRIALING,
                        expectedLastRenewDate,
                        null,
                        expectedCreatedAt,
                        expectedUpdatedAt
                );

        // when
        actualSubscription.execute(new IncompleteSubscription(expectedReason, expectedLastTransactionId));

        // then
        Assertions.assertNotNull(actualSubscription);
        Assertions.assertEquals(expectedId, actualSubscription.id());
        Assertions.assertEquals(expectedVersion, actualSubscription.version());
        Assertions.assertEquals(expectedAccountId, actualSubscription.accountId());
        Assertions.assertEquals(expectedPlanId, actualSubscription.planId());
        Assertions.assertEquals(expectedDueDate, actualSubscription.dueDate());
        Assertions.assertEquals(expectedStatus, actualSubscription.status().value());
        Assertions.assertEquals(expectedLastRenewDate, actualSubscription.lastRenewDate());
        Assertions.assertEquals(expectedLastTransactionId, actualSubscription.lastTransactionId());
        Assertions.assertEquals(expectedCreatedAt, actualSubscription.createdAt());
        Assertions.assertTrue(actualSubscription.updatedAt().isAfter(expectedUpdatedAt));

        Assertions.assertEquals(expectedEvents, actualSubscription.domainEvents().size());
        Assertions.assertInstanceOf(SubscriptionEvent.SubscriptionIncomplete.class, actualSubscription.domainEvents().getFirst());
    }


    @Test
    public void givenTrialSubscription_whenExecuteRenewCommand_shouldTransitToActiveState() {
        // given
        var expectedPlan = Fixture.Plans.plus();
        var expectedId = new SubscriptionId("SUB123");
        var expectedVersion = 0;
        var expectedAccountId = new AccountId("ACC123");
        var expectedPlanId = expectedPlan.id();
        var expectedStatus = SubscriptionStatus.ACTIVE;
        var expectedDueDate = LocalDate.now().plusMonths(1);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedLastTransactionId = UUID.randomUUID().toString();
        var expectedEvents = 1;

        var actualSubscription =
                Subscription.with(
                        expectedId,
                        expectedVersion,
                        expectedAccountId,
                        expectedPlanId,
                        LocalDate.now(),
                        SubscriptionStatus.TRIALING,
                        null,
                        null,
                        expectedCreatedAt,
                        expectedUpdatedAt
                );

        // when
        actualSubscription.execute(new RenewSubscription(expectedPlan, expectedLastTransactionId));

        // then
        Assertions.assertNotNull(actualSubscription);
        Assertions.assertEquals(expectedId, actualSubscription.id());
        Assertions.assertEquals(expectedVersion, actualSubscription.version());
        Assertions.assertEquals(expectedAccountId, actualSubscription.accountId());
        Assertions.assertEquals(expectedPlanId, actualSubscription.planId());
        Assertions.assertEquals(expectedDueDate, actualSubscription.dueDate());
        Assertions.assertEquals(expectedStatus, actualSubscription.status().value());
        Assertions.assertNotNull(actualSubscription.lastRenewDate());
        Assertions.assertEquals(expectedLastTransactionId, actualSubscription.lastTransactionId());
        Assertions.assertEquals(expectedCreatedAt, actualSubscription.createdAt());
        Assertions.assertTrue(actualSubscription.updatedAt().isAfter(expectedUpdatedAt));

        Assertions.assertEquals(expectedEvents, actualSubscription.domainEvents().size());
        Assertions.assertInstanceOf(SubscriptionEvent.SubscriptionRenewed.class, actualSubscription.domainEvents().getFirst());
    }

    @Test
    public void givenTrialSubscription_whenExecuteCancelCommand_shouldTransitToCanceledState() {
        // given
        var expectedId = new SubscriptionId("SUB123");
        var expectedVersion = 0;
        var expectedAccountId = new AccountId("ACC123");
        var expectedPlanId = new PlanId(123L);
        var expectedStatus = SubscriptionStatus.CANCELED;
        var expectedDueDate = LocalDate.now().plusMonths(1);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedLastRenewDate = InstantUtils.now();
        var expectedLastTransactionId = UUID.randomUUID().toString();
        var expectedEvents = 1;

        var actualSubscription =
                Subscription.with(
                        expectedId,
                        expectedVersion,
                        expectedAccountId,
                        expectedPlanId,
                        expectedDueDate,
                        SubscriptionStatus.TRIALING,
                        expectedLastRenewDate,
                        expectedLastTransactionId,
                        expectedCreatedAt,
                        expectedUpdatedAt
                );

        // when
        actualSubscription.execute(new CancelSubscription());

        // then
        Assertions.assertNotNull(actualSubscription);
        Assertions.assertEquals(expectedId, actualSubscription.id());
        Assertions.assertEquals(expectedVersion, actualSubscription.version());
        Assertions.assertEquals(expectedAccountId, actualSubscription.accountId());
        Assertions.assertEquals(expectedPlanId, actualSubscription.planId());
        Assertions.assertEquals(expectedDueDate, actualSubscription.dueDate());
        Assertions.assertEquals(expectedStatus, actualSubscription.status().value());
        Assertions.assertNotNull(actualSubscription.lastRenewDate());
        Assertions.assertEquals(expectedLastTransactionId, actualSubscription.lastTransactionId());
        Assertions.assertEquals(expectedCreatedAt, actualSubscription.createdAt());
        Assertions.assertTrue(actualSubscription.updatedAt().isAfter(expectedUpdatedAt));

        Assertions.assertEquals(expectedEvents, actualSubscription.domainEvents().size());
        Assertions.assertInstanceOf(SubscriptionEvent.SubscriptionCanceled.class, actualSubscription.domainEvents().getFirst());
    }

}
