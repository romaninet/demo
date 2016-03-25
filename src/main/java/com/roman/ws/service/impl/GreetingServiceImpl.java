package com.roman.ws.service.impl;

import com.roman.ws.model.Greeting;
import com.roman.ws.repository.GreetingRepository;
import com.roman.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Administrator on 3/24/16.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceImpl implements GreetingService {
    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);

    private GreetingRepository greetingRepository;

    @Autowired
    public GreetingServiceImpl(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @Override
    public Collection<Greeting> findAll() {
        logger.debug("inside findAll");

        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    @Override
    @Cacheable(value = "greetings", key = "#id")
    public Greeting findOne(Long id) {
        logger.debug("inside findOne with id={}", id);

        Greeting greeting = greetingRepository.findOne(id);
        return greeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#result.id")
    public Greeting create(Greeting greeting) {
        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (greeting.getId() != null) {
            // Cannot create Greeting with specified ID value
            return null;
        }

        Greeting savedGreeting = greetingRepository.save(greeting);

        //TODO: this is a test of transactional
        if (savedGreeting.getId() == 8L) {
            logger.error("Please Roll me back ( by Roman.S. ) !!");
            throw new RuntimeException("Please Roll me back ( by Roman.S. ) !!");
        }

        return savedGreeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#greeting.id")
    public Greeting update(Greeting greeting) {
        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        Greeting greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            // Cannot update Greeting that hasn't been persisted
            return null;
        }

        greetingToUpdate.setText(greeting.getText());
        Greeting updatedGreeting = greetingRepository.save(greetingToUpdate);
        return updatedGreeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CacheEvict(value = "greetings", key = "#id")
    public void delete(Long id) {
        greetingRepository.delete(id);
    }

    @Override
    @CacheEvict(value = "greetings", allEntries = true)
    public void evictCache() {
        //no implementation is needed
    }
}
