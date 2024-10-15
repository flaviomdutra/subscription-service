package com.fullcycle.subscription.domain.account;

import com.fullcycle.subscription.domain.account.idp.UserId;
import com.fullcycle.subscription.domain.exceptions.DomainException;
import com.fullcycle.subscription.domain.person.Document;
import com.fullcycle.subscription.domain.person.Email;
import com.fullcycle.subscription.domain.person.Name;
import com.fullcycle.subscription.domain.utils.InstantUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class AccountCreatedTest {

    @Test
    public void givenValidParams_whenInstantiateEvent_shouldReturnIt() {
        // given
        var expectedAccountId = new AccountId("ACC-123");
        var expectedUserId = new UserId("USER-123");
        var expectedName = new Name("Jonh", "Doe");
        var expectedEmail = new Email("jonh@gmail.com");
        var expectedDocument = Document.create("12345678912", "cpf");
        var expectedAggregateType = "Account";

        var actualAccount = Account.newAccount(expectedAccountId, expectedUserId, expectedEmail, expectedName, expectedDocument);

        // when
        var actualEvent = new AccountCreated(actualAccount);

        // then
        Assertions.assertNotNull(actualAccount);
        Assertions.assertEquals(expectedAccountId.value(), actualEvent.aggregateId());
        Assertions.assertEquals(expectedAggregateType, actualEvent.aggregateType());
        Assertions.assertEquals(expectedAccountId.value(), actualEvent.accountId());
        Assertions.assertEquals(expectedEmail.value(), actualEvent.email());
        Assertions.assertEquals(expectedName.fullname(), actualEvent.fullname());
        Assertions.assertNotNull(actualEvent.occurredOn());
    }

    @Test
    public void givenInvalidId_whenInstantiateEvent_shouldReturnError() {
        // given
        var expectedMessage = "'accountId' should not be empty";

        String expectedAccountId = null;
        var expectedName = "Jonh";
        var expectedEmail = "jonh@gmail.com";
        var expectedOccurredOn = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(DomainException.class, () ->
                new AccountCreated(expectedAccountId, expectedEmail, expectedName, expectedOccurredOn)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenInvalidEmail_whenInstantiateEvent_shouldReturnError() {
        // given
        var expectedMessage = "'email' should not be empty";

        var expectedAccountId = "ABC-123";
        var expectedName = "Jonh";
        var expectedEmail = "";
        var expectedOccurredOn = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(DomainException.class, () ->
                new AccountCreated(expectedAccountId, expectedEmail, expectedName, expectedOccurredOn)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenInvalidName_whenInstantiateEvent_shouldReturnError() {
        // given
        var expectedMessage = "'fullname' should not be empty";

        var expectedAccountId = "ABC-123";
        var expectedName = "";
        var expectedEmail = "jonh@gmail.com";
        var expectedOccurredOn = InstantUtils.now();

        // when
        var actualError = Assertions.assertThrows(DomainException.class, () ->
                new AccountCreated(expectedAccountId, expectedEmail, expectedName, expectedOccurredOn)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }

    @Test
    public void givenInvalidOccurredOn_whenInstantiateEvent_shouldReturnError() {
        // given
        var expectedMessage = "'occurredOn' should not be null";

        var expectedAccountId = "ABC-123";
        var expectedName = "John";
        var expectedEmail = "jonh@gmail.com";
        Instant expectedOccurredOn = null;

        // when
        var actualError = Assertions.assertThrows(DomainException.class, () ->
                new AccountCreated(expectedAccountId, expectedEmail, expectedName, expectedOccurredOn)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualError.getMessage());
    }
}
