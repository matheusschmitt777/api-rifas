package com.api.rifas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rifas.dto.OrderDTO;
import com.api.rifas.entities.Order;
import com.api.rifas.entities.OrderItem;
import com.api.rifas.entities.Raffle;
import com.api.rifas.servies.OrderService;

@RestController
@RequestMapping(value = "/orders")
@CrossOrigin(origins = "*")
public class OrderResource {

	@Autowired
	private OrderService service;

	@GetMapping
	public ResponseEntity<List<Order>> findAll() {
	    List<Order> orders = service.findAll();

	    for (Order order : orders) {
	        for (OrderItem item : order.getItems()) {
	            Raffle raffle = item.getRaffle();
	            raffle.generateRandomNumbers(item.getQuantity());
	        }
	    }
		return ResponseEntity.ok().body(orders);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		 Order obj = service.findById(id);

		    for (OrderItem item : obj.getItems()) {
		        Raffle raffle = item.getRaffle();
		        Integer quantity = item.getQuantity();
		        raffle.generateRandomNumbers(quantity);
		    }

		    return ResponseEntity.ok().body(obj);
	}
	
	 @PostMapping
	    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
	        Order newOrder = service.createOrder(orderDTO);

	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                .buildAndExpand(newOrder.getId()).toUri();

	        return ResponseEntity.created(uri).body(newOrder);
	    }
}
