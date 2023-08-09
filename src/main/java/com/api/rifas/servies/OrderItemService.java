package com.api.rifas.servies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.rifas.entities.OrderItem;
import com.api.rifas.repositories.OrderItemRepository;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository repository;
	
	 private Map<Long, Set<Integer>> raffleNumbersMap = new HashMap<>();

	public List<OrderItem> findAll(){
		return repository.findAll();
	} 

	public OrderItem findById(Long id) {
		Optional<OrderItem> obj = repository.findById(id);
		return obj.get();
	}
	
	public OrderItem insert(OrderItem obj) {
		return repository.save(obj);
	}
	public OrderItem updateQuantity(Long itemId, Integer newQuantity) {
        OrderItem item = repository.findById(itemId).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        item.setQuantity(newQuantity);
        return repository.save(item);
    }

	 public OrderItem save(OrderItem orderItem) {
	        return repository.save(orderItem);
	    }

	 public OrderItem createOrderItem(OrderItem orderItem) {
	        Long raffleId = orderItem.getRaffle().getId();
	        int maxNumber = orderItem.getRaffle().getQuantity();
	        int quantity = orderItem.getQuantity();
	        Set<Integer> generatedNumbers = new HashSet<>();

	        if (quantity <= maxNumber) {
	            generatedNumbers = raffleNumbersMap.getOrDefault(raffleId, new HashSet<>());

	            while (generatedNumbers.size() < quantity) {
	                int randomNumber = ThreadLocalRandom.current().nextInt(1, maxNumber + 1);
	                generatedNumbers.add(randomNumber);
	            }
	        }

	        orderItem.setGeneratedNumbers(generatedNumbers);
	        raffleNumbersMap.put(raffleId, generatedNumbers); // Atualiza os números gerados para a rifa
	        return repository.save(orderItem);
	    }
}
