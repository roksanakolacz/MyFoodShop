package com.myprojects.FoodStore.repository;

import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



    //Custom query
    @Query(value = "select * from product p where p.name like %:keyword%", nativeQuery = true)
    List<Product> findByKeyword(@Param("keyword") String keyword);


    @Query(value = "select * from product where product_category = :chosenCategory", nativeQuery = true)
    List<Product> findByCategory(@Param("chosenCategory") Integer chosenCategory);






}
