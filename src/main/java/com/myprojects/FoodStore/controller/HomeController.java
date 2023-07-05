package com.myprojects.FoodStore.controller;

import com.myprojects.FoodStore.ItemOperation;
import com.myprojects.FoodStore.LoginSession;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.repository.ProductRepository;
import com.myprojects.FoodStore.service.CartService;
import com.myprojects.FoodStore.service.ProductService;
import com.myprojects.FoodStore.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private UserService userService;

    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @GetMapping("/home")
    public String homePage(Model model, HttpSession httpSession){
        List<Product> products= productService.getAllProducts();
        model.addAttribute("products", products);

        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (userId != null) {
            User user = userService.findUserByUserId(userId);
            model.addAttribute("username", user.getUsername());
            if (user.isAdmin()){
                model.addAttribute("isAdmin", user.isAdmin());
            }
        }

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
