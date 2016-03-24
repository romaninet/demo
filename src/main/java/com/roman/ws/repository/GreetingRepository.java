package com.roman.ws.repository;

import com.roman.ws.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 3/24/16.
 */
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {
}
