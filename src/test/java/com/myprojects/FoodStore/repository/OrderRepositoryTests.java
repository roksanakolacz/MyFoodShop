package com.myprojects.FoodStore.repository;

import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.repository.UserRepository;
import com.myprojects.FoodStore.repository.order.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getOrderHistoryForUserId_userIdIsNull_returnNull(){

    List<Order> actualOrderList = orderRepository.getOrderHistoryForUser(null);

    Assertions.assertTrue(actualOrderList.isEmpty());

    }


    @Test
    public void getOrderHistoryForUser_userIdExists_returnOrderList(){

        User user = new User();
        userRepository.save(user);
        Integer userId = user.getUserId();

        List<Order> expectedOrderList = new ArrayList<>();


        expectedOrderList.add(orderRepository.save(Order.builder()
                .firstName("Roksana")
                .lastName("Kolacz")
                .address("Drochlin 29")
                .postCode("42-235")
                .city("Lelów")
                .created(LocalDateTime.now())
                .discountCode("PROMOCJA")
                .totalAmount(202.30)
                .userId(userId)
                .build()));

        expectedOrderList.add(orderRepository.save(Order.builder()
                .firstName("Filip")
                .lastName("Szala")
                .address("Wolsztyn 29")
                .postCode("42-235")
                .city("Lelów")
                .created(LocalDateTime.now())
                .discountCode("PROMOCJA")
                .totalAmount(202.30)
                .userId(userId)
                .build()));


        List<Order> actualOrderList = orderRepository.getOrderHistoryForUser(user.getUserId());


        Assertions.assertEquals(expectedOrderList, actualOrderList);

    }




    @Test
    public void getOrderHistoryForUser_userIdDoesNotExist_returnEmptyList(){

        Integer maxExistingUserId = userRepository.findAll().stream()
                .map(User::getUserId)
                .max(Comparator.naturalOrder())
                .orElse(null);


        Integer nonexistentUserId = maxExistingUserId + 1;

        List<Order> actualOrderList = orderRepository.getOrderHistoryForUser(nonexistentUserId);


        Assertions.assertTrue(actualOrderList.isEmpty());

    }


}
