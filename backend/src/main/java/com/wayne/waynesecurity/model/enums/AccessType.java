package com.wayne.waynesecurity.model.enums;

public enum AccessType {
	ENTRADA(1),
	SAIDA(2);
	
	private int code;
	
	private AccessType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static AccessType valueOf(int code) {
		for (AccessType value : AccessType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid Access Type code");
	}
}
