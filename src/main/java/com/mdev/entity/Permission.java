package com.mdev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    DIRECTOR_READ("director:read"),
    DIRECTOR_WRITE("director:write"),
    MANAGER_READ("manager:read"),
    MANAGER_WRITE("manager:write"),
    SPECIALIST_READ("specialist:read"),
    SPECIALIST_WRITE("specialist:write");

    private final String permission;
}
