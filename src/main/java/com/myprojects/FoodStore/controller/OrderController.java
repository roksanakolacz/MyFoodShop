package com.myprojects.FoodStore.controller;


import com.myprojects.FoodStore.dto.CustomerDataDto;
import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.OrderedItem;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.myprojects.FoodStore.ItemOperation;
import com.myprojects.FoodStore.service.CartService;
import com.myprojects.FoodStore.service.DiscountCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final CartService cartService;
    private final DiscountCodeService discountCodeService;
    private final OrderService orderService;

    @Autowired
    public OrderController(CartService cartService, DiscountCodeService discountCodeService, OrderService orderService) {
        this.cartService = cartService;
        this.discountCodeService = discountCodeService;
        this.orderService = orderService;
    }

    @GetMapping("/cart")
    public String showCart(Model model) {

        String discountCodeErrorMessage = cartService.getDiscountCodeErrorMessage();

        if (discountCodeErrorMessage != null) {
            model.addAttribute("discountCodeErrorMessage", discountCodeErrorMessage);
        }

        return "cartView";
    }



    @GetMapping("/decrease/{itemId}")
    public String decreaseItem(@PathVariable("itemId") Long itemId){

        cartService.itemOperation(itemId, ItemOperation.DECREASE);
        return "cartView";
    }


    @GetMapping("/increase/{itemId}")
    public String increaseItem(@PathVariable("itemId") Long itemId){

        cartService.itemOperation(itemId, ItemOperation.INCREASE);
        return "cartView";
    }


    @GetMapping("/remove/{itemId}")
    public String removeItem(@PathVariable("itemId") Long itemId){
        cartService.itemOperation(itemId, ItemOperation.REMOVE);
        return "cartView";

    }

    @PostMapping("/applyDiscount")
    public String applyDiscount(@RequestParam("discountCode") String discountCode) {

        cartService.clearError();
        cartService.applyDiscountCode(discountCode);


        return "redirect:/order/cart";
    }


    @GetMapping("/summary")
    public String showSummary(){

        return "summary";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(CustomerDataDto customerDataDto, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");

        orderService.saveOrder(customerDataDto, userId);
        return "redirect:/home";

    }

    @GetMapping("/orders")
    public String showOrders(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<Order> orders = orderService.getOrderHistoryForUser(userId);
        model.addAttribute("orders", orders);
        return "listOfOrders";
    }


    @GetMapping("/showOrderedProducts/{orderId}")
    public String showOrderedProducts(Model model, @PathVariable("orderId") Integer orderId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<OrderedItem> orderedProducts = orderService.getOrderedItems(orderId);
        model.addAttribute("orderedProducts", orderedProducts);
        return "listOfOrderedProducts";
    }



}
