package com.wayne.waynesecurity.model.enums;

public enum AccessArea {
	SALA_BATMAN(1),
    LABORATORIO(2),
    ARMAZEM(3),
    GARAGEM(4),
    ESCRITORIO(5);
	
	private int code;
	
	private AccessArea(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static AccessArea valueOf(int code) {
		for (AccessArea value : AccessArea.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid Access Area code");
	}
}
