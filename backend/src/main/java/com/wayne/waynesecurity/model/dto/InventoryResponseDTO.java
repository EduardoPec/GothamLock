package com.wayne.waynesecurity.model.dto;

import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.model.enums.InventoryStatus;
import com.wayne.waynesecurity.model.enums.InventoryType;

import java.io.Serializable;
import java.util.Objects;

public class InventoryResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private InventoryType type;
    private InventoryStatus status;

    public InventoryResponseDTO() {
    }

    public InventoryResponseDTO(Long id, String name, InventoryType type, InventoryStatus status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
    }

    public static InventoryResponseDTO fromEntity(Inventory inventory) {
        return new InventoryResponseDTO(
                inventory.getId(),
                inventory.getName(),
                inventory.getType(),
                inventory.getStatus()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
        this.type = type;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InventoryResponseDTO that = (InventoryResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InventoryResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
