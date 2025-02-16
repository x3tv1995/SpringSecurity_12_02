package ru.lesson.springsecurity_12_02.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
}
