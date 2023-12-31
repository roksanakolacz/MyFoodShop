package com.myprojects.FoodStore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Long orderId;
    private int amount;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderedItem(Long orderId, int amount, Product product) {
        this.orderId = orderId;
        this.amount = amount;
        this.product = product;
    }


}
