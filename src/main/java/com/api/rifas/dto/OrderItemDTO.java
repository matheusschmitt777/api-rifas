package com.api.rifas.dto;

import java.util.Set;

public class OrderItemDTO {

    private Long id;
    private Long orderId;
    private Long raffleId;
    private Integer quantity;
    private Set<Integer> generatedNumbers;
    private String errorMessage; // Mensagem de erro

    // Construtor padrão
    public OrderItemDTO() {
    }

    // Construtor para definir uma mensagem de erro
    public OrderItemDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getRaffleId() {
		return raffleId;
	}

	public void setRaffleId(Long raffleId) {
		this.raffleId = raffleId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Set<Integer> getGeneratedNumbers() {
		return generatedNumbers;
	}

	public void setGeneratedNumbers(Set<Integer> generatedNumbers) {
		this.generatedNumbers = generatedNumbers;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
