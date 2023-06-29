package com.myprojects.FoodStore.repository;

import com.myprojects.FoodStore.model.DiscountCode;
import com.myprojects.FoodStore.model.Product;
import org.hibernate.query.criteria.JpaInPredicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCodesRepository  extends JpaRepository<DiscountCode, Long> {
}
