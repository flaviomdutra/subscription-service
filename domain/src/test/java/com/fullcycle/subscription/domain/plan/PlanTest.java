package com.fullcycle.subscription.domain.plan;

import com.fullcycle.subscription.domain.exceptions.DomainException;
import com.fullcycle.subscription.domain.utils.InstantUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class PlanTest {

    @Test
    public void givenValidParams_whenCallsNewPlan_shouldInstantiate() {
        // given
        var expectedId = new PlanId("PLN-123");
        var expectedVersion = 0;
        var expectedName = "Plus";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = true;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);

        // when
        var actualPlan = Plan.newPlan(expectedId, expectedName, expectedDescription, expectedActive, expectedPrice);

        // then
        Assertions.assertNotNull(actualPlan);
        Assertions.assertEquals(expectedId, actualPlan.id());
        Assertions.assertEquals(expectedVersion, actualPlan.version());
        Assertions.assertEquals(expectedName, actualPlan.name());
        Assertions.assertEquals(expectedDescription, actualPlan.description());
        Assertions.assertEquals(expectedActive, actualPlan.active());
        Assertions.assertEquals(expectedPrice, actualPlan.price());
        Assertions.assertNotNull(actualPlan.createdAt());
        Assertions.assertNotNull(actualPlan.updatedAt());
        Assertions.assertNull(actualPlan.deletedAt());
    }

    @Test
    public void givenNullActive_whenCallsNewPlan_shouldInstantiate() {
        // given
        var expectedId = new PlanId("PLN-123");
        var expectedVersion = 0;
        var expectedName = "Plus";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);

        // when
        var actualPlan = Plan.newPlan(expectedId, expectedName, expectedDescription, null, expectedPrice);

        // then
        Assertions.assertNotNull(actualPlan);
        Assertions.assertEquals(expectedId, actualPlan.id());
        Assertions.assertEquals(expectedVersion, actualPlan.version());
        Assertions.assertEquals(expectedName, actualPlan.name());
        Assertions.assertEquals(expectedDescription, actualPlan.description());
        Assertions.assertEquals(expectedActive, actualPlan.active());
        Assertions.assertEquals(expectedPrice, actualPlan.price());
        Assertions.assertNotNull(actualPlan.createdAt());
        Assertions.assertNotNull(actualPlan.updatedAt());
        Assertions.assertNotNull(actualPlan.deletedAt());
    }

    @Test
    public void givenValidParams_whenCallsWith_shouldInstantiate() {
        // given
        var expectedId = new PlanId("PLN-123");
        var expectedVersion = 0;
        var expectedName = "Plus";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualPlan = Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt);

        // then
        Assertions.assertNotNull(actualPlan);
        Assertions.assertEquals(expectedId, actualPlan.id());
        Assertions.assertEquals(expectedVersion, actualPlan.version());
        Assertions.assertEquals(expectedName, actualPlan.name());
        Assertions.assertEquals(expectedDescription, actualPlan.description());
        Assertions.assertEquals(expectedActive, actualPlan.active());
        Assertions.assertEquals(expectedPrice, actualPlan.price());
        Assertions.assertEquals(expectedCreatedAt, actualPlan.createdAt());
        Assertions.assertEquals(expectedUpdatedAt, actualPlan.updatedAt());
        Assertions.assertEquals(expectedDeletedAt, actualPlan.deletedAt());
    }

    @Test
    public void givenInvalidId_whenCallsWith_shouldReturnError() {
        // given
        var expectedMessage = "'id' should not be null";
        PlanId expectedId = null;
        var expectedVersion = 0;
        var expectedName = "Plus";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenInvalidName_whenCallsWith_shouldReturnError() {
        // given
        var expectedMessage = "'name' should not be empty";
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenNullName_whenCallsWith_shouldReturnError() {
        // given
        var expectedMessage = "'name' should not be empty";
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        String expectedName = null;
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenInvalidDescription_whenCallsWith_shouldReturnError() {
        // given
        var expectedMessage = "'description' should not be empty";
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "Plano";
        var expectedDescription = "";
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenNullDescription_whenCallsWith_shouldReturnError() {
        // given
        var expectedMessage = "'description' should not be empty";
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "Plano";
        String expectedDescription = null;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenNullActive_whenCallsWith_shouldReturnFalse() {
        // given
        var expectedMessage = "'active' should not be null";

        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "Plano";
        var expectedDescription = """
                Lorem Ipsum
                """;
        Boolean expectedActive = null;
        var expectedPrice = new MonetaryAmount("BRL", 20.99);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenNullPrice_whenCallsWith_shouldReturnError() {
        // given
        var expectedMessage = "'price' should not be null";
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "Plano";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        MonetaryAmount expectedPrice = null;
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void given0AsPrice_whenCallsWith_shouldReturnOK() {
        // given
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "Plano";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 0.0);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualPlan = Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt);

        // then
        Assertions.assertNotNull(actualPlan);
        Assertions.assertEquals(expectedId, actualPlan.id());
        Assertions.assertEquals(expectedVersion, actualPlan.version());
        Assertions.assertEquals(expectedName, actualPlan.name());
        Assertions.assertEquals(expectedDescription, actualPlan.description());
        Assertions.assertEquals(expectedActive, actualPlan.active());
        Assertions.assertEquals(expectedPrice, actualPlan.price());
        Assertions.assertEquals(expectedCreatedAt, actualPlan.createdAt());
        Assertions.assertEquals(expectedUpdatedAt, actualPlan.updatedAt());
        Assertions.assertEquals(expectedDeletedAt, actualPlan.deletedAt());
    }

    @Test
    public void givenNullCreatedAt_whenCallsWith_shouldReturnError() {
        // given
        var expectedMessage = "'createdAt' should not be null";
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "Plano";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 0.0);
        Instant expectedCreatedAt = null;
        var expectedUpdatedAt = InstantUtils.now();
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenNullUpdatedAt_whenCallsWith_shouldReturnError() {
        // given
        var expectedMessage = "'updatedAt' should not be null";
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "Plano";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 0.0);
        var expectedCreatedAt = InstantUtils.now();
        Instant expectedUpdatedAt = null;
        var expectedDeletedAt = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenNullUpdatedAt_whenCallsWith_shouldReturnOK() {
        // given
        var expectedId = new PlanId("123");
        var expectedVersion = 0;
        var expectedName = "Plano";
        var expectedDescription = """
                Lorem Ipsum
                """;
        var expectedActive = false;
        var expectedPrice = new MonetaryAmount("BRL", 20.0);
        var expectedCreatedAt = InstantUtils.now();
        var expectedUpdatedAt = InstantUtils.now();
        Instant expectedDeletedAt = null;

        // when
        var actualPlan = Plan.with(expectedId, expectedVersion, expectedName, expectedDescription, expectedActive, expectedPrice, expectedCreatedAt, expectedUpdatedAt, expectedDeletedAt);

        // then
        Assertions.assertNotNull(actualPlan);
        Assertions.assertEquals(expectedId, actualPlan.id());
        Assertions.assertEquals(expectedVersion, actualPlan.version());
        Assertions.assertEquals(expectedName, actualPlan.name());
        Assertions.assertEquals(expectedDescription, actualPlan.description());
        Assertions.assertEquals(expectedActive, actualPlan.active());
        Assertions.assertEquals(expectedPrice, actualPlan.price());
        Assertions.assertEquals(expectedCreatedAt, actualPlan.createdAt());
        Assertions.assertEquals(expectedUpdatedAt, actualPlan.updatedAt());
        Assertions.assertEquals(expectedDeletedAt, actualPlan.deletedAt());
    }
}