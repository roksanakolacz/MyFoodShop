package com.myprojects.FoodStore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Component
@Entity
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




}