package com.myprojects.FoodStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.logging.Logger;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class FoodStoreApplication {

	public static void main(String[] args) {


		SpringApplication.run(FoodStoreApplication.class, args);


	}

}
