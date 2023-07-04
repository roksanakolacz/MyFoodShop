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
    @GeneratedValue
    private Integer userId;
    private String username;
    private String password;
    private String email;

    @OneToMany
    @JoinColumn(name="userId")
    private List<Order> orderList;


    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}