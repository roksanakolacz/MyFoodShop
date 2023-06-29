package com.myprojects.FoodStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class DiscountCode {
    @Id
    @GeneratedValue
    private Long codeId;
    private String code;
    private double discountPercentage;
    private LocalDate expiryDate;

    public DiscountCode(String code, double discountPercentage, LocalDate expiryDate) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.expiryDate = expiryDate;
    }
}
