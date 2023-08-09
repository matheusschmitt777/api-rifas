package com.api.rifas.servies;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.rifas.entities.OrderItem;
import com.api.rifas.repositories.OrderItemRepository;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository repository;

	@Autowired
	private RaffleNumberService raffleNumberService;

	public List<OrderItem> findAll() {
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

		Set<Integer> generatedNumbers = raffleNumberService.generateNumbers(raffleId, quantity, maxNumber);

		orderItem.setGeneratedNumbers(generatedNumbers);
		return repository.save(orderItem);
	}
}
