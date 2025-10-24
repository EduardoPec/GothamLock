package com.wayne.waynesecurity.model.enums;

public enum InventoryType {
		EQUIPAMENTO(1),
		VEICULO(2),
		DISPOSITIVO(3);
	
	private int code;

	private InventoryType(int code) {
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

