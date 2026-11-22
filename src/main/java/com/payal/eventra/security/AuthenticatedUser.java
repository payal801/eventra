package com.payal.eventra.security;

import com.payal.eventra.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticatedUser {

    private Long id;
    private String email;
    private Role role;
}