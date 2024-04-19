package com.company.sortnumbers.service;

import static com.company.sortnumbers.util.constant.ConstSettings.MAX_NUMBER;
import static com.company.sortnumbers.util.constant.ConstSettings.MIN_NUMBER;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class NumberService {

    public List<Integer> generateNumbers(int numberCount) {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < numberCount; i++) {
            int number = random.nextInt(MAX_NUMBER) + 1;
            numbers.add(number);
        }

        if (numbers.stream().noneMatch(number -> number <= MIN_NUMBER)) {
            int index = random.nextInt(numbers.size());
            int number = random.nextInt(MIN_NUMBER) + 1;
            numbers.set(index, number);
        }

        return numbers;
    }

    public boolean shouldGenerateNewNumbers(int number) {
        return number <= MIN_NUMBER;
    }

    public List<Integer> sortNumberButtons(List<Integer> numberButtons, boolean ascending) {
        return numberButtons.stream()
            .sorted(ascending ? Comparator.naturalOrder() : Comparator.reverseOrder())
            .collect(Collectors.toList());
    }
}
