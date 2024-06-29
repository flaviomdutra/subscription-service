package com.fullcycle.subscription.domain.person;

import com.fullcycle.subscription.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NameTest {

    @Test
    public void givenValidNames_whenInstantiate_shouldReturnVO() {
        // given
        var expectedFirstName = "Jonh";
        var expectedLastName = "Doe";

        // when
        var actualName = new Name(expectedFirstName, expectedLastName);

        // then
        assertEquals(expectedFirstName, actualName.firstname());
        assertEquals(expectedLastName, actualName.lastname());
    }

    @Test
    public void givenInvalidFirstName_whenInstantiate_shouldReturnError() {
        // given
        String expectedFirstName = null;
        var expectedError = "'firstname' should not be empty";
        var expectedLastName = "Doe";

        // when
        var actualError = assertThrows(DomainException.class, () -> new Name(expectedFirstName, expectedLastName));

        // then
        assertEquals(expectedError, actualError.getMessage());
    }


    @Test
    public void givenInvalidLastName_whenInstantiate_shouldReturnError() {
        // given
        var expectedFirstName = "Jonh";
        var expectedError = "'lastname' should not be empty";
        String expectedLastName = null;

        // when
        var actualError = assertThrows(DomainException.class, () -> new Name(expectedFirstName, expectedLastName));

        // then
        assertEquals(expectedError, actualError.getMessage());
    }
}
