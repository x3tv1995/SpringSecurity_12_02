package ru.lesson.springsecurity_12_02.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.lesson.springsecurity_12_02.model.enums.Role;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
