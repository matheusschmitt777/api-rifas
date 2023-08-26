package com.api.rifas.entities.enums;

public enum UserCommissionStatus {
	
	WAITING_PAYMENT(1),
	PAID(2);

	private int code;

	private UserCommissionStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static UserCommissionStatus valueOf(int code) {
		for (UserCommissionStatus value : UserCommissionStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid UserCommissionStatus code");
	}
}
