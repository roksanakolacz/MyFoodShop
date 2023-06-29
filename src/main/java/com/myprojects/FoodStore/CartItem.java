package com.myprojects.FoodStore;

import com.myprojects.FoodStore.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItem {
    private Product product;
    private int counter;
    private double price;


    public CartItem(Product product){
        this.product = product;
        this.counter = 1;
        this.price = product.getPrice();
    }

    public void recalculatePrice() {
        price = Math.round(product.getPrice() * counter * 100) / 100.0;
    }

    public void increaseCounter(){
        counter++;
        recalculatePrice();
    }

    public void decreaseCounter(){
        if(counter > 0) {
            counter--;
            recalculatePrice();
        }

    }

    public boolean hasZeroItems(){

        return counter == 0;
    }


    //metoda do porównywania itemów po Id
    public boolean isEquals(Product product)
    {
        return this.product.getId().equals(product.getId());

    }


}
