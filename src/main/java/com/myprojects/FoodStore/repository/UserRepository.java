package com.myprojects.FoodStore.repository;

import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    User findByUserName(@Param("username") String username);


}
