package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.Cart;
import com.myprojects.FoodStore.CartItem;
import com.myprojects.FoodStore.LoginSession;
import com.myprojects.FoodStore.dto.CustomerDataDto;
import com.myprojects.FoodStore.mapper.OrderMapper;
import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.OrderedItem;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.repository.ProductRepository;
import com.myprojects.FoodStore.repository.order.OrderItemRepository;
import com.myprojects.FoodStore.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final Cart cart;
    private final CartService cartService;

    private final LoginSession session;

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(Cart cart, CartService cartService, LoginSession session, OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.cart = cart;
        this.cartService = cartService;
        this.session = session;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void saveOrder(CustomerDataDto customerDataDto, Integer userId){
        Order order = OrderMapper.mapToOrder(customerDataDto, cart, cartService, userId);
        orderRepository.save(order);
        orderItemRepository.saveAll(OrderMapper.mapToOrderItemList(cart, order));
        updateStock(cart);
        cart.cleanCart();
    }


    public void updateStock(Cart cart){
        for(CartItem cartItem : cart.getCartItems()){

            Product product = cartItem.getProduct();
            Integer productId = product.getId();
            Integer orderedQuantity = cartItem.getCounter();

            Product existingProduct = productRepository.findById(Long.valueOf(productId)).orElse(null);
            if (existingProduct != null) {
                Integer currentStock = existingProduct.getQuantity();
                Integer updatedStock = currentStock - orderedQuantity;

                existingProduct.setQuantity(updatedStock);
                productRepository.save(existingProduct);
            }

        }
    }

    public List<Order> getOrderHistoryForUser(Integer userId){

        List<Order> orders = orderRepository.getOrderHistoryForUser(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        orders.forEach(order -> {
            order.setFormattedDate(order.getCreated().format(formatter));
            order.setTotalAmount(Math.round(order.getTotalAmount() * 100.0) / 100.0);
        });

        return orders;
    }


    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        orders.forEach(order -> {
            order.setFormattedDate(order.getCreated().format(formatter));
            order.setTotalAmount(Math.round(order.getTotalAmount() * 100.0) / 100.0);
        });

        return orders;
    }

    public List<OrderedItem> getOrderedItems(Long orderId){

        return orderItemRepository.getOrderedItemsForOrder(orderId);

    }

}
