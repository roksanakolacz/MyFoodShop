package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.Cart;
import com.myprojects.FoodStore.model.DiscountCode;
import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.repository.DiscountCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountCodeService{

    @Autowired
    private DiscountCodesRepository discountCodesRepository;




    public List<DiscountCode> getAllDiscountCodes(){
        List<DiscountCode> discountCodes = (List<DiscountCode>)discountCodesRepository.findAll();

        return discountCodes;
    }


    public void addDiscountCode(DiscountCode discountCode){

        discountCodesRepository.save(discountCode);
    }

    public void removeDiscountCode(DiscountCode discountCode){
        discountCodesRepository.delete(discountCode);
    }


    public DiscountCode getDiscountCode(String code) {
        for (DiscountCode discountCode : discountCodesRepository.findAll()) {
            if (discountCode.getCode().equals(code)) {
                return discountCode;
            }
        }
        return null;
    }


    public boolean isDiscountCodeValid(String code) {
        Optional<DiscountCode> discountCodeOptional = getAllDiscountCodes().stream()
                .filter(i -> i.getCode().equals(code))
                .findFirst();

        return discountCodeOptional.isPresent() && !discountCodeOptional.get().getExpiryDate().isBefore(LocalDate.now());
    }



}
