package com.wayne.waynesecurity.model.dto;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.enums.AccessArea;
import com.wayne.waynesecurity.model.enums.AccessResult;
import com.wayne.waynesecurity.model.enums.AccessType;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;

public class AccessLogRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "Área de acesso é obrigatória")
    private AccessArea area;

    @NotNull(message = "Tipo de acesso é obrigatório")
    private AccessType type;

    @NotNull(message = "Resultado do acesso é obrigatório")
    private AccessResult result;

    private Instant moment;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;

    public AccessLogRequestDTO() {
    }

    public AccessLog toEntity(User user) {
        AccessLog accessLog = new AccessLog();
        accessLog.setId(this.id);
        accessLog.setArea(this.area);
        accessLog.setType(this.type);
        accessLog.setResult(this.result);
        accessLog.setMoment(this.moment != null ? this.moment : Instant.now());
        accessLog.setUser(user);
        return accessLog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccessArea getArea() {
        return area;
    }

    public void setArea(AccessArea area) {
        this.area = area;
    }

    public AccessType getType() {
        return type;
    }

    public void setType(AccessType type) {
        this.type = type;
    }

    public AccessResult getResult() {
        return result;
    }

    public void setResult(AccessResult result) {
        this.result = result;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
