package com.api.rifas.servies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class RaffleNumberService {

    private Map<Long, Set<Integer>> raffleNumbersMap = new HashMap<>();

    public Set<Integer> generateNumbers(Long raffleId, int quantity, int maxNumber) {
        Set<Integer> generatedNumbers = new HashSet<>();

        if (quantity <= maxNumber) {
            Set<Integer> existingNumbers = raffleNumbersMap.getOrDefault(raffleId, new HashSet<>());

            while (generatedNumbers.size() < quantity) {
                int randomNumber = ThreadLocalRandom.current().nextInt(1, maxNumber + 1);

                if (!existingNumbers.contains(randomNumber)) {
                    generatedNumbers.add(randomNumber);
                    existingNumbers.add(randomNumber);
                }
            }

            raffleNumbersMap.put(raffleId, existingNumbers); // Atualiza os números gerados para a rifa
        }

        return generatedNumbers;
    }

}
