package com.fullcycle.subscription.domain.subscription.status;

import com.fullcycle.subscription.domain.Fixture;
import com.fullcycle.subscription.domain.account.AccountId;
import com.fullcycle.subscription.domain.subscription.Subscription;
import com.fullcycle.subscription.domain.subscription.SubscriptionId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrialingSubscriptionStatusTest {

    @Test
    public void givenInstances_whenCallsToString_shouldReturnValue() {
        // given
        var expectedString = "trialing";
        var one = new TrialingSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus()));

        // when
        var actualString = one.toString();

        // then
        assertEquals(expectedString, actualString);
    }

    @Test
    public void givenTwoInstances_whenCallsEquals_shouldCompareClasses() {
        // given
        var expectedEquals = true;
        var one = new TrialingSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus()));
        var two = new TrialingSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB2"), new AccountId("ACC123"), Fixture.Plans.plus()));

        // when
        var actualEquals = one.equals(two);

        // then
        assertEquals(expectedEquals, actualEquals, "O equals deveria levar em conta apenas a classe do status e não a subscription");
    }

    @Test
    public void givenTwoInstances_whenCallsHashCode_shouldBeEquals() {
        // given
        var expectedEquals = true;
        var one = new TrialingSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus()));
        var two = new TrialingSubscriptionStatus(Subscription.newSubscription(new SubscriptionId("SUB2"), new AccountId("ACC123"), Fixture.Plans.plus()));

        // then
        assertEquals(one.hashCode(), two.hashCode(), "O hashCode deveria levar em conta apenas a classe do status e não a subscription");
    }

    @Test
    public void givenTrialingStatus_whenCallsActive_shouldTransitToActiveStatus() {
        // given
        var expectedStatusClass = ActiveSubscriptionStatus.class;
        var expectedSubscription = Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus());
        var target = new TrialingSubscriptionStatus(expectedSubscription);

        // when
        target.active();

        // then
        assertEquals(expectedStatusClass, expectedSubscription.status().getClass());
    }

    @Test
    public void givenTrialingStatus_whenCallsCancel_shouldTransitToCanceledStatus() {
        // given
        var expectedStatusClass = CanceledSubscriptionStatus.class;
        var expectedSubscription = Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus());
        var target = new TrialingSubscriptionStatus(expectedSubscription);

        // when
        target.cancel();

        // then
        assertEquals(expectedStatusClass, expectedSubscription.status().getClass());
    }

    @Test
    public void givenTrialingStatus_whenCallsTrialing_shouldDoNothing() {
        // given
        var expectedStatusClass = TrialingSubscriptionStatus.class;
        var expectedSubscription = Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus());
        var target = new TrialingSubscriptionStatus(expectedSubscription);

        // when
        target.trialing();

        // then
        assertEquals(expectedStatusClass, expectedSubscription.status().getClass());
    }

    @Test
    public void givenTrialingStatus_whenCallsIncomplete_shouldTransitToIncompleteStatus() {
        // given
        var expectedStatusClass = IncompleteSubscriptionStatus.class;
        var expectedSubscription = Subscription.newSubscription(new SubscriptionId("SUB"), new AccountId("ACC123"), Fixture.Plans.plus());
        var target = new TrialingSubscriptionStatus(expectedSubscription);

        // when
        target.incomplete();

        // then
        assertEquals(expectedStatusClass, expectedSubscription.status().getClass());
    }
}