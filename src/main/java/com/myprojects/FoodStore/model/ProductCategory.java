package com.myprojects.FoodStore.model;

import java.util.HashMap;
import java.util.Map;

public enum ProductCategory {
    DAIRY(0),
    BAKING(1),
    GRAIN(2),
    MEET(3),
    VEGETABLES(4),
    SWEETS(5),
    DRINKS(6),
    ACCESSORIES(7);


    private int value;
    private static Map map = new HashMap<>();

    private ProductCategory(int value) {
        this.value = value;
    }

    static {
        for (ProductCategory pageType : ProductCategory.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static ProductCategory valueOf(int productCategory) {

        return (ProductCategory) map.get(productCategory);
    }

    public int getValue() {

        return value;
    }
}
