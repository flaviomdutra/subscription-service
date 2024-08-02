package com.fullcycle.subscription.domain.subscription.status;

import com.fullcycle.subscription.domain.Fixture;
import com.fullcycle.subscription.domain.account.AccountId;
import com.fullcycle.subscription.domain.exceptions.DomainException;
import com.fullcycle.subscription.domain.subscription.Subscription;
import com.fullcycle.subscription.domain.subscription.SubscriptionId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ActiveSubscriptionStatusTest {

    private static Subscription activeSubscription() {
        var expectedSubscription = Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus());
        expectedSubscription.status().active();
        return expectedSubscription;
    }

    @Test
    public void givenInstances_whenCallsToString_shouldReturnValue() {
        // given
        var expectedString = "active";
        var one = new ActiveSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus()));

        // when
        var actualString = one.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    public void givenTwoInstances_whenCallsEquals_shouldCompareClasses() {
        // given
        var expectedEquals = true;
        var one = new ActiveSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus()));
        var two = new ActiveSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB2"), new AccountId("ACC123"), Fixture.Plans.plus()));

        // when
        var actualEquals = one.equals(two);

        // then
        assertEquals(expectedEquals, actualEquals, "O equals deveria levar em conta apenas a classe do status e não a subscription");
    }

    @Test
    public void givenTwoInstances_whenCallsHashCode_shouldBeEquals() {
        // given
        var expectedEquals = true;
        var one = new ActiveSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus()));
        var two = new ActiveSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB2"), new AccountId("ACC123"), Fixture.Plans.plus()));

        // then
        assertEquals(one.hashCode(), two.hashCode(), "O hashCode deveria levar em conta apenas a classe do status e não a subscription");
    }

    @Test
    public void givenActiveStatus_whenCallsTrialing_shouldExpectError() {
        // given
        var expectedError = "Subscription with status active can't transit to trialing";
        var expectedStatusClass = ActiveSubscriptionStatus.class;
        var expectedSubscription = activeSubscription();

        var target = new ActiveSubscriptionStatus(expectedSubscription);

        // when
        var actualError = assertThrows(DomainException.class, () -> target.trialing());

        // then
        assertEquals(expectedError, actualError.getMessage());
        assertEquals(expectedStatusClass, expectedSubscription.status().getClass());
    }

    @Test
    public void givenActiveStatus_whenCallsCancel_shouldTransitToCanceledStatus() {
        // given
        var expectedStatusClass = CanceledSubscriptionStatus.class;
        var expectedSubscription = activeSubscription();
        var target = new ActiveSubscriptionStatus(expectedSubscription);

        // when
        target.cancel();

        // then
        assertEquals(expectedStatusClass, expectedSubscription.status().getClass());
    }

    @Test
    public void givenActiveStatus_whenCallsActive_shouldDoNothing() {
        // given
        var expectedStatusClass = ActiveSubscriptionStatus.class;
        var expectedSubscription = activeSubscription();
        var target = new ActiveSubscriptionStatus(expectedSubscription);

        // when
        target.active();

        // then
        assertEquals(expectedStatusClass, expectedSubscription.status().getClass());
    }

    @Test
    public void givenActiveStatus_whenCallsIncomplete_shouldTransitToIncompleteStatus() {
        // given
        var expectedStatusClass = IncompleteSubscriptionStatus.class;
        var expectedSubscription = activeSubscription();
        var target = new ActiveSubscriptionStatus(expectedSubscription);

        // when
        target.incomplete();

        // then
        assertEquals(expectedStatusClass, expectedSubscription.status().getClass());
    }
}