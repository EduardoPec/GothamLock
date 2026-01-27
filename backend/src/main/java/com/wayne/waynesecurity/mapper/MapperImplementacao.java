package com.wayne.waynesecurity.mapper;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.dto.request.AccessLogRequestDTO;
import com.wayne.waynesecurity.model.dto.request.InventoryRequestDTO;
import com.wayne.waynesecurity.model.dto.request.UserRequestDTO;
import com.wayne.waynesecurity.model.dto.response.AccessLogResponseDTO;
import com.wayne.waynesecurity.model.dto.response.InventoryResponseDTO;
import com.wayne.waynesecurity.model.dto.response.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MapperImplementacao implements Imapper {

    @Override
    public AccessLog accessLogToEntity(AccessLogRequestDTO request, User user) {
        AccessLog accessLog = new AccessLog();
        accessLog.setId(request.getId());
        accessLog.setArea(request.getArea());
        accessLog.setType(request.getType());
        accessLog.setResult(request.getResult());
        accessLog.setMoment(request.getMoment() != null ? request.getMoment() : Instant.now());
        accessLog.setUser(user);
        return accessLog;
    }

    @Override
    public Inventory inventoryToEntity(InventoryRequestDTO inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryRequest.getId());
        inventory.setName(inventoryRequest.getName());
        inventory.setType(inventoryRequest.getType());
        inventory.setStatus(inventoryRequest.getStatus());
        return inventory;
    }

    @Override
    public User userToEntity(UserRequestDTO userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        user.setPassword(userRequest.getPassword());
        return user;
    }

    @Override
    public AccessLogResponseDTO toAccessLogResponse(AccessLog accessLog) {
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

    @Override
    public InventoryResponseDTO toInventoryResponse(Inventory inventory) {
        return new InventoryResponseDTO(
                inventory.getId(),
                inventory.getName(),
                inventory.getType(),
                inventory.getStatus()
        );
    }

    @Override
    public UserResponseDTO toUserResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
