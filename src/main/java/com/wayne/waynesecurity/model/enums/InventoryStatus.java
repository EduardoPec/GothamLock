package com.wayne.waynesecurity.model.enums;

public enum InventoryStatus {
	DISPONIVEL(1),
	EM_USO(2),
	MANUTENCAO(3);
	
	private int code;
	
	private InventoryStatus(int code) {
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

