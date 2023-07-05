package com.myprojects.FoodStore;

import com.zaxxer.hikari.hibernate.HikariConnectionProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
@ComponentScan("com.myprojects.FoodStore")
public class FoodStoreApplication {


	public static void main(String[] args) {



		SpringApplication.run(FoodStoreApplication.class, args);


	}

}
