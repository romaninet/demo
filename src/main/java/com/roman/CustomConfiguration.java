package com.roman;

import com.roman.ws.model.Greeting;
import com.roman.ws.service.GreetingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by Administrator on 3/25/16.
 */
@Configuration
public class CustomConfiguration {
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

    @Bean
    public CacheManager cacheManager(){
        GuavaCacheManager cacheManager = new GuavaCacheManager("greetings");
        return cacheManager;
    }
}
