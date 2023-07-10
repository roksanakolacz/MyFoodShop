package com.myprojects.FoodStore.repository;

import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.model.ProductCategory;
import com.myprojects.FoodStore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Mock
    private ProductRepository mockProductRepository;

    @Test
    public void findProductByKeyword_productsMatchToLowercaseKeyword_returnListOfProducts(){

        String keyword = "opi";

        Product product1 = new Product("Długopis", ProductCategory.SWEETS, 10, 10,"xyz");

        productRepository.save(product1);

        List<Product> actualProductList = productRepository.findProductByKeyword(keyword);

        Assertions.assertFalse(actualProductList.isEmpty());
        Assertions.assertTrue(actualProductList.contains(product1));

    }

    @Test
    public void findProductByKeyword_productsMatchToUppercaseKeyword_returnListOfProducts(){

        String keyword = "OPI";

        Product product1 = new Product("Długopis", ProductCategory.SWEETS, 10, 10,"xyz");

        productRepository.save(product1);

        List<Product> actualProductList = productRepository.findProductByKeyword(keyword);

        Assertions.assertFalse(actualProductList.isEmpty());
        Assertions.assertTrue(actualProductList.contains(product1));

    }

    @Test
    public void findProductByKeyword_productsDoNotMatchToKeyword_returnEmptyList(){

        String keyword = "KOPI";

        List<Product> actualProductList = productRepository.findProductByKeyword(keyword);

        Assertions.assertTrue(actualProductList.isEmpty());


    }

    @Test
    public void findProductByKeyword_emptyKeyword_returnFullList(){

        String keyword = "";

        List<Product> actualProductList = productRepository.findProductByKeyword(keyword);

        Assertions.assertTrue(actualProductList.containsAll(productRepository.findAll()));


    }


    @Test
    public void findProductByKeyword_keywordIsNull_returnNull() {
        String keyword = null;

        List<Product> actualProductList = productRepository.findProductByKeyword(keyword);

        Assertions.assertTrue(actualProductList.isEmpty());
    }


    @Test
    public void findProductsByCategory_categoryExists_returnProductList(){

        Integer category = 1;

        Product product1 = new Product("Pączek", ProductCategory.BAKING, 10, 10,"xyz");

        productRepository.save(product1);


        List<Product> actualProductList = productRepository.findProductsByCategory(category);

        Assertions.assertTrue(actualProductList.contains(product1));

    }

    @Test
    public void findProductsByCategory_categoryExistsProductNot_returnEmptyList() {
        Integer category = 7;


        List<Product> actualProductList = productRepository.findProductsByCategory(category);

        Assertions.assertTrue(actualProductList.isEmpty());
    }

    @Test
    public void findProductsByCategory_categoryDoesNotExists_returnEmptyList() {
        Integer category = 100;

        when(mockProductRepository.findProductsByCategory(category)).thenReturn(Collections.emptyList());

        List<Product> actualProductList = productRepository.findProductsByCategory(category);

        Assertions.assertTrue(actualProductList.isEmpty());
    }

    @Test
    public void findProductsByCategory_categoryIsNull_returnEmptyList(){

        Integer category = null;

        List<Product> actualProductList = productRepository.findProductsByCategory(category);

        Assertions.assertTrue(actualProductList.isEmpty());

    }


}
