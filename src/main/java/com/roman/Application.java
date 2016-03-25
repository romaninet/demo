package com.roman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot main application with some Java configuration
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Application {

    //We also use custom java config class.

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
