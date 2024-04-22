package com.company.sortnumbers.service;

import static com.company.sortnumbers.util.constant.ConstSettings.MAX_NUMBER;
import static com.company.sortnumbers.util.constant.ConstSettings.MIN_NUMBER;

import com.company.sortnumbers.util.model.SwapPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class NumberService {

    private final Set<SwapPair> swapPairs = new LinkedHashSet<>();
    private boolean ascending = true;

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

    public Set<SwapPair> quickSort(List<Integer> numbers) {
        swapPairs.clear();
        sort(numbers, 0, numbers.size() - 1);
        ascending = !ascending;
        return swapPairs;
    }

    private int partition(List<Integer> numbers, int low, int high) {
        int pivotValue = numbers.get(high);
        int storeIndex = low;

        for (int i = low; i < high; i++) {
            if (ascending ? numbers.get(i) < pivotValue : numbers.get(i) > pivotValue) {
                if (i != storeIndex) {
                    swapAndRecord(numbers, i, storeIndex);
                }
                storeIndex++;
            }
        }

        if (storeIndex != high) {
            swapAndRecord(numbers, storeIndex, high);
        }

        return storeIndex;
    }

    private void swapAndRecord(List<Integer> numbers, int i, int j) {
        Collections.swap(numbers, i, j);
        swapPairs.add(SwapPair.builder()
            .first(numbers.get(i))
            .second(numbers.get(j))
            .build());
    }

    private void sort(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(numbers, low, high);
            sort(numbers, low, pivotIdx - 1);
            sort(numbers, pivotIdx + 1, high);
        }
    }
}
