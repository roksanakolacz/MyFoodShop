package com.myprojects.FoodStore.repository;

import com.myprojects.FoodStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where binary username = :username", nativeQuery = true)
    User findUserByUsername(@Param("username") String username);


}
