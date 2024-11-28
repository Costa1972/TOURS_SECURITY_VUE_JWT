package ru.costa.tours_vue_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public enum UserDto {;
    private interface Id {@Positive Long getId();}
    private interface Username {@Positive String getUsername();}
    private interface Email {@NotBlank String getEmail();}
}
