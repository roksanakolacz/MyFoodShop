package com.myprojects.FoodStore;

import com.zaxxer.hikari.hibernate.HikariConnectionProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.apache.logging.log4j.Logger;




@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class FoodStoreApplication {

	public static void main(String[] args) {



		SpringApplication.run(FoodStoreApplication.class, args);


	}

}
