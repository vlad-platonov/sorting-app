package com.company.sortnumbers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.company.sortnumbers.util.constant.ConstSettings;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumberServiceTest {

    private NumberService numberService;

    @BeforeEach
    void setUp() {
        numberService = new NumberService();
    }

    @Test
    void should_ReturnListWithSizeEqualToNumberCount_When_GenerateNumbersIsCalled() {
        int numberCount = 5;

        List<Integer> numbers = numberService.generateNumbers(numberCount);

        assertEquals(numberCount, numbers.size());
    }

    @Test
    void should_IncludeAtLeastOneNumberLessThanOrEqualToMinNumber_When_GenerateNumbersIsCalled() {
        int numberCount = 5;

        List<Integer> numbers = numberService.generateNumbers(numberCount);

        assertTrue(numbers.stream().anyMatch(number -> number <= ConstSettings.MIN_NUMBER));
    }

    @Test
    void should_ReturnTrue_When_NumberIsLessThanOrEqualToMinNumber() {
        int number = ConstSettings.MIN_NUMBER;

        boolean shouldGenerate = numberService.shouldGenerateNewNumbers(number);

        assertTrue(shouldGenerate);
    }

    @Test
    void should_ReturnFalse_When_NumberIsMoreThanMinNumber() {
        int number = ConstSettings.MIN_NUMBER + 1;

        boolean shouldGenerate = numberService.shouldGenerateNewNumbers(number);

        assertFalse(shouldGenerate);
    }

    @Test
    void should_SortNumbersInAscendingOrder_When_SortNumberButtonsIsCalledWithTrue() {
        List<Integer> numbers = List.of(3, 1, 2);

        List<Integer> sortedNumbers = numberService.sortNumberButtons(numbers, true);

        assertEquals(List.of(1, 2, 3), sortedNumbers);
    }

    @Test
    void should_SortNumbersInDescendingOrder_When_SortNumberButtonsIsCalledWithFalse() {
        List<Integer> numbers = List.of(3, 1, 2);

        List<Integer> sortedNumbers = numberService.sortNumberButtons(numbers, false);

        assertEquals(List.of(3, 2, 1), sortedNumbers);
    }
}
