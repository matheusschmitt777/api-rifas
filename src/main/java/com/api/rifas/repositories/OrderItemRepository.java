package com.api.rifas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rifas.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
