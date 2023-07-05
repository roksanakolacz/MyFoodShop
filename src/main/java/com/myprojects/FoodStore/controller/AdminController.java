package com.myprojects.FoodStore.controller;

import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.repository.ProductRepository;
import com.myprojects.FoodStore.service.OrderService;
import com.myprojects.FoodStore.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {


    private final ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @GetMapping("/adminPage")
    public String adminPage() {
        return "adminView/addItem";
    }

    @PostMapping("/adminPage")
    public String addProduct(Product product) {
        productRepository.save(product);
        return "adminView/addItem";
    }

    @GetMapping("/items")
    public String getProducts(Model model, @RequestParam(defaultValue = "name") String sort) {
        List<Product> products = productService.getAllProductsSortedBy(sort);
        model.addAttribute("products", products);
        return "adminView/showProducts";
    }

    @GetMapping("/allOrders")
    public String showOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "adminView/allOrdersList";
    }

    @GetMapping("/items/{itemId}/remove")
    public String removeItem(@PathVariable("itemId") Long itemId) {
        try {
            productRepository.deleteById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/items";
    }

    @GetMapping("/items/{itemId}/edit")
    public String showEditProductForm(@PathVariable("itemId") Long itemId, Model model) {
        Product product = productService.getById(itemId);
        model.addAttribute("product", product);
        return "adminView/editItem";
    }

    @PostMapping("/items/{itemId}/edit")
    public String editProduct(@PathVariable("itemId") Long itemId, @ModelAttribute("product") Product product, Model model) {
        productService.editProduct(itemId, product);
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "redirect:/items";
    }

    @RequestMapping(path = {"/", "/search"})
    public String searchByName(Model model, String keyword) {
        if (keyword != null) {
            List<Product> products = productService.getByName(keyword);
            model.addAttribute("products", products);
        } else {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
        }
        return "adminView/showProducts";
    }

    @RequestMapping(path = {"/", "/searchByCategory"})
    public String searchByCategory(Model model, String chosenCategory) {
        if (!chosenCategory.equalsIgnoreCase("ALL")) {
            List<Product> products = productService.getByCategory(chosenCategory);
            model.addAttribute("products", products);
        } else {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
        }
        return "adminView/showProducts";
    }



}
