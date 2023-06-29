package com.myprojects.FoodStore.repository.order;

import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "select * from orders where user_Id = :userId", nativeQuery = true)
    List<Order> getOrderHistoryForUser(@Param("userId") Integer userId);



}
