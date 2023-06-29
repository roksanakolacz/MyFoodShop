package com.myprojects.FoodStore.mapper;

import com.myprojects.FoodStore.Cart;
import com.myprojects.FoodStore.CartItem;
import com.myprojects.FoodStore.dto.CustomerDataDto;
import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.OrderedItem;
import com.myprojects.FoodStore.service.CartService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class OrderMapper{

    public static Order mapToOrder(CustomerDataDto customerDataDto, Cart cart, CartService cartService, Integer userId){
        return Order.builder()
                .firstName(customerDataDto.getFirstName())
                .lastName(customerDataDto.getLastName())
                .address(customerDataDto.getAddress())
                .postCode(customerDataDto.getPostCode())
                .city(customerDataDto.getCity())
                .created(LocalDateTime.now())
                .discountCode(cartService.getDiscountCodeUsed())
                .totalAmount(cart.getSum())
                .userId(userId)
                .build();
    }


    public static List<OrderedItem> mapToOrderItemList(Cart cart, Order order){
        List<OrderedItem> orderItems = new ArrayList<>();
        for(CartItem ci : cart.getCartItems()){

            orderItems.add(new OrderedItem(order.getOrderId(), ci.getCounter(), ci.getProduct()));
        }
        return orderItems;
    }
}
