package com.roman.ws.service;

import com.roman.ws.model.Greeting;

import java.util.Collection;

/**
 * Created by Administrator on 3/24/16.
 */
public interface GreetingService {
    Collection<Greeting> findAll();

    Greeting findOne(Long id);

    Greeting create(Greeting greeting);

    Greeting update(Greeting greeting);

    void delete(Long id);

    void evictCache();
}
