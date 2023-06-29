package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.Cart;
import com.myprojects.FoodStore.ItemOperation;
import com.myprojects.FoodStore.model.DiscountCode;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.repository.ProductRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
public class CartService {

    private final ProductRepository productRepository;

    private DiscountCodeService discountCodeService;
    private final Cart cart;
    private String discountCodeErrorMessage;

    private String discountCodeUsed;


    //wstrzyknięcie przez konstruktor
    @Autowired
    public CartService(ProductRepository productRepository, DiscountCodeService discountCodeService, Cart cart) {
        this.productRepository = productRepository;
        this.discountCodeService = discountCodeService;
        this.cart = cart;
    }

    public List<Product> getAllItems(){

        return productRepository.findAll();
    }




    public void itemOperation(Long itemId, ItemOperation itemOperation){

        Optional<Product> oProduct = productRepository.findById(itemId);
        if(oProduct.isPresent()){
            Product product = oProduct.get();
            switch (itemOperation){
                case INCREASE -> cart.addProduct(product);
                case DECREASE -> cart.decreaseProduct(product);
                case REMOVE -> cart.removeAllProducts(product);
                default -> throw new IllegalArgumentException();
            }
        }

        if(cart.isDiscountCodeUsed()){
            cart.setDiscountCodeUsed(false);
            applyDiscountCode(discountCodeUsed);
        }
    }


    public void applyDiscountCode(String code) {

        if (discountCodeService.isDiscountCodeValid(code)&&cart.isDiscountCodeUsed()==false){
            DiscountCode discountCode = discountCodeService.getDiscountCode(code);
            double discountAmount = cart.getSum() * discountCode.getDiscountPercentage() / 100;
            cart.setSum(cart.getSum() - discountAmount);
            cart.setDiscountCodeUsed(true);
            discountCodeUsed=code;
        } else{
            discountCodeErrorMessage="Wrong discount code";
        }
    }

    public void clearError(){
        discountCodeErrorMessage="";
    }
}
