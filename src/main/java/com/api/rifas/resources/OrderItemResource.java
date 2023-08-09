package com.api.rifas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rifas.dto.OrderItemDTO;
import com.api.rifas.entities.Order;
import com.api.rifas.entities.OrderItem;
import com.api.rifas.entities.Raffle;
import com.api.rifas.servies.OrderItemService;
import com.api.rifas.servies.OrderService;
import com.api.rifas.servies.RaffleService;

@RestController
@RequestMapping(value = "/order-items")
@CrossOrigin(origins = "*")
public class OrderItemResource {

    @Autowired
    private OrderItemService orderItemService;
    
    @Autowired
    private RaffleService raffleService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderItem>> findAll() {
        List<OrderItem> orderItems = orderItemService.findAll();
        return ResponseEntity.ok().body(orderItems);
    }

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItemDTO dto) {
        Order order = orderService.findById(dto.getOrderId());
        Raffle raffle = raffleService.findById(dto.getRaffleId());

        OrderItem newOrderItem = new OrderItem(order, raffle, dto.getQuantity(), raffle.getPrice());
        orderItemService.createOrderItem(newOrderItem);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(order, raffle) // Use o ID do novo OrderItem aqui
            .toUri();

        return ResponseEntity.created(location).body(newOrderItem);
    }
}