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

public interface Imapper {
    AccessLog accessLogToEntity(AccessLogRequestDTO request, User user);
    Inventory inventoryToEntity(InventoryRequestDTO inventoryRequest);
    User userToEntity(UserRequestDTO userRequest);

    AccessLogResponseDTO toAccessLogResponse(AccessLog accessLog);
    InventoryResponseDTO toInventoryResponse(Inventory inventory);
    UserResponseDTO toUserResponse(User user);
}
