package com.myprojects.FoodStore.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Component
@Entity
@Builder
@Table(name="users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String password;

    @Transient
    private char[] passwordChars;

    private String email;

    private boolean isAdmin;

    @OneToMany
    @JoinColumn(name="userId")
    private List<Order> orderList;


    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = false;
    }
}