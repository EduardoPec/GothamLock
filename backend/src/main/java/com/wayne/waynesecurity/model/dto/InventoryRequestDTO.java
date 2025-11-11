package com.wayne.waynesecurity.model.dto;

import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.model.enums.InventoryStatus;
import com.wayne.waynesecurity.model.enums.InventoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class InventoryRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Nome do item é obrigatório")
    private String name;

    @NotNull(message = "Tipo do item é obrigatório")
    private InventoryType type;

    @NotNull(message = "Status do item é obrigatório")
    private InventoryStatus status;

    public InventoryRequestDTO() {
    }

    public Inventory toEntity() {
        Inventory inventory = new Inventory();
        inventory.setId(this.id);
        inventory.setName(this.name);
        inventory.setType(this.type);
        inventory.setStatus(this.status);
        return inventory;
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
}
