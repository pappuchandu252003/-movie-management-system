package com.moviemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieManagementSystemApplication.class, args);
        System.out.println("------------------------moviemanagement is running------------------------------------");
    }

}
