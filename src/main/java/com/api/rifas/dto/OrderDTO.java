package com.api.rifas.dto;

import java.io.Serializable;
import java.time.Instant;

public class OrderDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Instant moment;
	private Long clientId;
	
	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
