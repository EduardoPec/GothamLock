package com.wayne.waynesecurity.model.enums;

public enum AccessResult {
	NEGADO(1),
	AUTORIZADO(2);
	
	private int code;
	
	private AccessResult(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static Role valueOf(int code) {
		for (Role value : Role.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid Role code");
	}
}
