package com.myprojects.FoodStore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name="orders")
public class Order {

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String firstName;
    private String lastName;
    private String address;
    private String postCode;
    private String city;
    private LocalDateTime created;
    private String discountCode;
    private double totalAmount;

    @OneToMany
    @JoinColumn(name="orderId")
    private List<OrderedItem> orderedItems;


    private Integer userId;

    @Transient
    private String formattedDate;


}
