package com.wayne.waynesecurity.model.enums;

public enum Role {
    FUNCIONARIO(1),
    GERENTE(2),
    ADMIN_SEGURANCA(3);

    private int code;

    private Role(int code) {
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