package com.myprojects.FoodStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private ProductCategory productCategory;
    private Integer quantity;
    private double price;
    private String imgUrl;


    public Product(String name, ProductCategory productCategory, Integer quantity, double price, String imgUrl) {
        this.name = name;
        this.productCategory = productCategory;
        this.quantity = quantity;
        this.price = price;
        this.imgUrl = imgUrl;
    }


}
