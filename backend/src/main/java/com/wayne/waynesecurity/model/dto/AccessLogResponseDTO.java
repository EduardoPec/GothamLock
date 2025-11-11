package com.wayne.waynesecurity.model.dto;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.model.enums.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class AccessLogResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private AccessArea area;
    private AccessType type;
    private AccessResult result;
    private Instant moment;
    private String userName;
    private String userEmail;
    private Long userId;

    public AccessLogResponseDTO() {
    }

    public AccessLogResponseDTO(Long id, AccessArea area, AccessType type, AccessResult result, Instant moment, String userName, String userEmail, Long userId) {
        this.id = id;
        this.area = area;
        this.type = type;
        this.result = result;
        this.moment = moment;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    public static AccessLogResponseDTO fromEntity(AccessLog accessLog) {
        return new AccessLogResponseDTO(
                accessLog.getId(),
                accessLog.getArea(),
                accessLog.getType(),
                accessLog.getResult(),
                accessLog.getMoment(),
                accessLog.getUser().getName(),
                accessLog.getUser().getEmail(),
                accessLog.getUser().getId()
        );
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccessLogResponseDTO that = (AccessLogResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AccessLogResponseDTO{" +
                "area=" + area +
                ", id=" + id +
                ", type=" + type +
                ", result=" + result +
                ", moment=" + moment +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userId=" + userId +
                '}';
    }
}
