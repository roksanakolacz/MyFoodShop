package com.myprojects.FoodStore.repository;

import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.OrderedItem;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.model.ProductCategory;
import com.myprojects.FoodStore.repository.ProductRepository;
import com.myprojects.FoodStore.repository.order.OrderItemRepository;
import com.myprojects.FoodStore.repository.order.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderItemRepositoryTests {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getOrderedItemsForOrder_orderIdIsNull_returnEmptyList(){

        List<OrderedItem> expectedOrderedItemList = new ArrayList<>();


        List<OrderedItem> actualOrderedItemList = orderItemRepository.getOrderedItemsForOrder(null);


        Assertions.assertEquals(actualOrderedItemList.size(), 0);
        Assertions.assertEquals(actualOrderedItemList, expectedOrderedItemList);
    }

    @Test
    public void getOrderedItemsForOrder_nonexistentOrderId_returnEmptyList() {

        Long maxExistingOrderId = orderRepository.findAll().stream()
                .map(Order::getOrderId)
                .max(Comparator.naturalOrder())
                .orElse(0L);

        Long nonexistentOrderId = maxExistingOrderId + 1;

        List<OrderedItem> orderedItems = orderItemRepository.getOrderedItemsForOrder(nonexistentOrderId);

        Assertions.assertTrue(orderedItems.isEmpty());
    }



    @Test
    public void getOrderedItemsForOrder_orderExists_returnListOfOrderedItems(){
        //Arrange
        Order order = new Order();
        orderRepository.save(order);
        Long orderId = order.getOrderId();


        List<OrderedItem> expectedOrderedItemList = new ArrayList<>();

        Product product1 = new Product("Herbata", ProductCategory.DRINKS, 10, 10, "img1.img" );
        Product product2 = new Product("Kawa", ProductCategory.DRINKS, 22, 10, "img2.img");

        productRepository.save(product1);
        productRepository.save(product2);

        OrderedItem orderedItem1 = new OrderedItem(orderId, 2, product1);
        OrderedItem orderedItem2 = new OrderedItem(orderId, 12, product2);

        orderItemRepository.save(orderedItem1);
        orderItemRepository.save(orderedItem2);

        expectedOrderedItemList.add(orderedItem1);
        expectedOrderedItemList.add(orderedItem2);

        //Act
        List<OrderedItem> actualOrderedItemList = orderItemRepository.getOrderedItemsForOrder(orderId);


        //Assert
        Assertions.assertEquals(2, actualOrderedItemList.size());
        Assertions.assertEquals(expectedOrderedItemList, actualOrderedItemList);
    }


}
