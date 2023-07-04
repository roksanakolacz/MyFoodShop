package com.myprojects.FoodStore.repository.order;

import com.myprojects.FoodStore.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderedItem, Integer> {

    @Query(value = "SELECT * FROM ordered_item oi JOIN product p ON oi.product_id = p.id " +
            "WHERE oi.order_id = :orderId",
            nativeQuery = true)
    List<OrderedItem> getOrderedItemsForOrder(@Param("orderId") Long orderId);

}
