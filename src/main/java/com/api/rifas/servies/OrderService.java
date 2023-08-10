package com.api.rifas.servies;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rifas.dto.OrderDTO;
import com.api.rifas.entities.Order;
import com.api.rifas.entities.User;
import com.api.rifas.repositories.OrderRepository;
import com.api.rifas.repositories.UserRepository;
import com.api.rifas.servies.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public List<Order> findAll() {
		return repository.findAll();
	}

	@Transactional
	public Order findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
    public Order createOrder(OrderDTO orderDTO) {
        User client = userRepository.findById(orderDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException(orderDTO.getClientId()));

        Order newOrder = new Order();
        newOrder.setClient(client);
        newOrder.setMoment(Instant.now()); // Adicione o Instant de emissão

        return repository.save(newOrder);
    }
}
