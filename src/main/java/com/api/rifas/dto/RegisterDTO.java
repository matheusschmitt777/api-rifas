package com.api.rifas.dto;

import com.api.rifas.entities.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
