package com.mdev.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.mdev.entity.Permission.*;

@RequiredArgsConstructor
@Getter
public enum UserRoles {

    DIRECTOR(Set.of(DIRECTOR_READ, DIRECTOR_WRITE)),
    MANAGER(Set.of(DIRECTOR_READ, MANAGER_WRITE)),
    SPECIALIST(Set.of(DIRECTOR_READ, SPECIALIST_WRITE));

    private final Set<Permission> permissions;


    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}

