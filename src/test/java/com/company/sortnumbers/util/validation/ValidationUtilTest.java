package com.company.sortnumbers.util.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.company.sortnumbers.util.constant.ConstMessages.Messages;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidationUtilTest {

    private ValidationUtil validationUtil;

    @BeforeEach
    void setUp() {
        validationUtil = new ValidationUtil();
    }

    @Test
    void should_ReturnEmptyOptional_When_InputIsValid() {
        String validInput = "5";

        Optional<String> result = validationUtil.validate(validInput);

        assertTrue(result.isEmpty());
    }

    @Test
    void should_ReturnNotEmptyOptionalWithErrorMessage_When_InputIsEmpty() {
        String emptyInput = "";

        Optional<String> result = validationUtil.validate(emptyInput);

        assertTrue(result.isPresent());
        assertEquals(Messages.EMPTY_FIELD_ERROR, result.get());
    }

    @Test
    void should_ReturnNotEmptyOptionalWithErrorMessage_When_InputIsNotInteger() {
        String notIntegerInput = "abc";

        Optional<String> result = validationUtil.validate(notIntegerInput);

        assertTrue(result.isPresent());
        assertEquals(Messages.NOT_INTEGER_ERROR, result.get());
    }

    @Test
    void should_ReturnNotEmptyOptionalWithErrorMessage_When_InputIsNotPositive() {
        String notPositiveInput = "-5";

        Optional<String> result = validationUtil.validate(notPositiveInput);

        assertTrue(result.isPresent());
        assertEquals(Messages.NOT_POSITIVE_ERROR, result.get());
    }
}
