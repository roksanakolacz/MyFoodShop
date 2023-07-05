package com.myprojects.FoodStore;

import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.service.DiscountCodeService;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Component
@Scope(value = "session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();
    private int counter = 0;
    private double sum = 0.0;

    private boolean discountCodeUsed = false;

    private DiscountCodeService discountCodeService;


   private static final Logger logger = LogManager.getLogger(Cart.class);


    public void recalculatePriceAndCounter(){

        sum = BigDecimal.valueOf(cartItems.stream()
                        .mapToDouble(CartItem::getPrice)
                        .sum())
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();


        counter = cartItems.stream()
                .mapToInt(CartItem::getCounter)
                .reduce(0, Integer::sum);

    }


    public Optional<CartItem> getCartItemByProduct(Product product){
        return cartItems.stream()
                .filter(i->i.isEquals(product))
                .findFirst();
    }


    public void addProduct(Product product){
        getCartItemByProduct(product).ifPresentOrElse(
                CartItem::increaseCounter,
                ()->cartItems.add(new CartItem(product)));

        logger.info("Produkt dodany do koszyka");

        recalculatePriceAndCounter();
    }


    public void decreaseProduct(Product product){

        Optional<CartItem> oCartItem = getCartItemByProduct(product);
        if(oCartItem.isPresent()){
            CartItem cartItem = oCartItem.get();
            cartItem.decreaseCounter();
            if(cartItem.hasZeroItems()){
                removeAllProducts(product);
            }
            else {
                recalculatePriceAndCounter();
            }
        }
    }


    public void removeAllProducts(Product product){
        cartItems.removeIf(i->i.isEquals(product));
        recalculatePriceAndCounter();
        if (cartItems.size()==0) {
            discountCodeUsed = false;
        }

    }


    public void cleanCart(){
        cartItems.clear();
        counter=0;
        sum=0.0;
        discountCodeUsed = false;
    }




}
