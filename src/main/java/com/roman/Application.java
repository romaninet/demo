package com.roman;

import com.roman.ws.model.Greeting;
import com.roman.ws.service.GreetingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

/**
 * Spring boot main application with some Java configuration
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(GreetingService greetingService) {
        return args -> {
            Arrays.asList("aaa,bbb,ccc,ddd,eee".split(",")).forEach(
                    s -> {
                        Greeting greeting = new Greeting();
                        greeting.setText(s);
                        greetingService.create(greeting);
                    }
            );
        };
    }
}
