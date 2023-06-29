package com.myprojects.FoodStore;

import com.myprojects.FoodStore.Cart;
import com.myprojects.FoodStore.CartItem;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.model.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartTest {

    private Cart cart;
    private List<CartItem> cartItems;

    @BeforeEach
    void setUp() {

        cart = new Cart();


        Product product1 = new Product("Długopis", ProductCategory.SWEETS, 10, 10,"xyz");
        Product product2 = new Product( "Zeszyt", ProductCategory.SWEETS, 15, 15,"xyz2");


        CartItem item1 = new CartItem(product1);
        item1.setCounter(2);
        item1.setPrice(20);

        CartItem item2 = new CartItem(product2);
        item2.setCounter(3);
        item2.setPrice(45.0);


        cartItems = new ArrayList<>();
        cartItems.add(item1);
        cartItems.add(item2);


        cart.setCartItems(cartItems);
    }

    @Test
    void recalculatePriceAndCounter_sumAndCounterCorrect_True() {

        cart.recalculatePriceAndCounter();


        double expectedSum = 65.0;
        int expectedCounter = 5;


        Assertions.assertEquals(expectedSum, cart.getSum(), 0.01);
        Assertions.assertEquals(expectedCounter, cart.getCounter());
    }

/*
    @Test
    public void getCartItemByProduct_existingProduct_True(){
        Product product = new Product("Długopis", ProductCategory.SWEETS, 10, 10,"xyz");
        CartItem cartItem = new CartItem(product);

        Optional<CartItem> result = cart.getCartItemByProduct(product);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(cartItem.getProduct().getName(), result.get().getProduct().getName());
    }


 */





}
