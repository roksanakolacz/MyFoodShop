package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.model.ProductCategory;
import com.myprojects.FoodStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProducts(){
        List<Product> products =  (List<Product>)productRepository.findAll();
        return products;
    }


    public List<Product> getAllProductsSortedBy(String sortBy) {
        // retrieve the list of employees from the repository
        List<Product> products =  (List<Product>)productRepository.findAll();

        // sort the list based on the given parameter
        switch (sortBy) {
            case "name":
                products.sort(Comparator.comparing(Product::getName));
                break;
            case "category":
                products.sort(Comparator.comparing(Product::getProductCategory));
                break;
            case "quantity":
                products.sort(Comparator.comparing(Product::getQuantity));
                break;
            case "price":
                products.sort(Comparator.comparing(Product::getPrice));
                break;
            default:
                // do nothing
        }

        return products;
    }

    public List<Product> getByName(String keyword){
        return productRepository.findByKeyword(keyword);
    }

    public List<Product> getByCategory(String chosenCategory){
        return productRepository.findByCategory(ProductCategory.valueOf(chosenCategory).getValue());
    }

    public Product getById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
    }


    @Transactional
    public void editProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setProductCategory(updatedProduct.getProductCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setImgUrl(updatedProduct.getImgUrl());
        productRepository.save(existingProduct);
    }






}
