package com.myprojects.FoodStore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDataDto {
    private String firstName;
    private String lastName;
    private String address;
    private String postCode;
    private String city;
    private Integer userId;
}
