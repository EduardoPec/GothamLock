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
	
	public static AccessResult valueOf(int code) {
		for (AccessResult value : AccessResult.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid Access Result code");
	}
}
