package com.api.rifas.entities.enums;

public enum ActionCommissionStatus {
	
	WAITING_PAYMENT(1),
	PAID(2);

	private int code;

	private ActionCommissionStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ActionCommissionStatus valueOf(int code) {
		for (ActionCommissionStatus value : ActionCommissionStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid ActionCommissionStatus code");
	}
}
