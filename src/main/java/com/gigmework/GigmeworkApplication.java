package com.gigmework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the gigmework backend application.
 *
 * This is a minimal Spring Boot application. Running the main method will
 * start the embedded web server. Additional controllers, services and
 * repositories defined in the same package or sub-packages will be
 * automatically detected and registered.
 */
@SpringBootApplication
public class GigmeworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(GigmeworkApplication.class, args);
    }
}