package com.myprojects.FoodStore.controller;

import com.myprojects.FoodStore.ItemOperation;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.repository.ProductRepository;
import com.myprojects.FoodStore.service.CartService;
import com.myprojects.FoodStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {


    private final ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @GetMapping("/home")
    public String homePage(Model model){
        List<Product> products= productService.getAllProducts();
        model.addAttribute("products", products);
        return "homePage";
    }



    @RequestMapping(path = {"/","/homesearch"})
    public String searchByName(Model model, String keyword) {
        if(keyword!=null) {
            List<Product> products = productService.getByName(keyword);
            model.addAttribute("products", products);
        }else {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);}
        return "homePage";
    }

    @RequestMapping(path = {"/", "/homesearchByCategory"})
    public String searchByCategory(Model model, String chosenCategory){

        if(!chosenCategory.equalsIgnoreCase("ALL")) {

            List<Product> products = productService.getByCategory(chosenCategory);
            model.addAttribute("products", products);}
        else {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);}

        return "homePage";
    }


    @GetMapping("/add/{itemId}")
    public String addItemtoCart(@PathVariable("itemId") Long itemId, Model model){
        cartService.itemOperation(itemId, ItemOperation.INCREASE);
        List<Product> products= cartService.getAllItems();
        model.addAttribute("products", products);
        return "homePage";
    }






}
