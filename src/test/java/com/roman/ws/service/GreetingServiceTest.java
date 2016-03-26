package com.roman.ws.service;

import com.roman.ws.AbstractTest;
import com.roman.ws.model.Greeting;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Unit test methods for the GreetingService and GreetingServiceBean.
 *
 */
@Transactional
public class GreetingServiceTest extends AbstractTest {

    @Autowired
    private GreetingService service;

    //2 from sql file + 5 from config file
    private final int TOTAL_GREETINGS = 7;

    @Before
    public void setUp() {
        service.evictCache();
    }

    @After
    public void tearDown() {
        // clean up after each test method
    }

    @Test
    public void testFindAll() {
        Collection<Greeting> list = service.findAll();
        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected list size", TOTAL_GREETINGS, list.size());
    }

    @Test
    public void testFindOne() {
        Long id = new Long(1);
        Greeting entity = service.findOne(id);
        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id, entity.getId());
    }

    @Test
    public void testFindOneNotFound() {
        Long id = Long.MAX_VALUE;
        Greeting entity = service.findOne(id);
        Assert.assertNull("failure - expected null", entity);
    }

    @Test
    public void testCreate() {
        Greeting entity = new Greeting();
        entity.setText("test");
        Greeting createdEntity = service.create(entity);
        Assert.assertNotNull("failure - expected not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", "test", createdEntity.getText());
        Collection<Greeting> list = service.findAll();
        Assert.assertEquals("failure - expected size", TOTAL_GREETINGS + 1, list.size());
    }

    @Test
    public void testCreateWithId() {
        Exception exception = null;
        Greeting entity = new Greeting();
        entity.setId(Long.MAX_VALUE);
        entity.setText("test");

        try {
            service.create(entity);
        } catch (EntityExistsException e) {
            exception = e;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected EntityExistsException", exception instanceof EntityExistsException);
    }

    @Test
    public void testUpdate() {
        Long id = new Long(1);
        Greeting entity = service.findOne(id);
        Assert.assertNotNull("failure - expected not null", entity);
        String updatedText = entity.getText() + " test";
        entity.setText(updatedText);
        Greeting updatedEntity = service.update(entity);

        Assert.assertNotNull("failure - expected not null", updatedEntity);
        Assert.assertEquals("failure - expected id attribute match", id, updatedEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", updatedText, updatedEntity.getText());
    }

    @Test
    public void testUpdateNotFound() {
        Exception exception = null;
        Greeting entity = new Greeting();
        entity.setId(Long.MAX_VALUE);
        entity.setText("test");

        try {
            service.update(entity);
        } catch (NoResultException e) {
            exception = e;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected NoResultException", exception instanceof NoResultException);

    }

    @Test
    public void testDelete() {
        Long id = new Long(1);
        Greeting entity = service.findOne(id);
        Assert.assertNotNull("failure - expected not null", entity);
        service.delete(id);
        Collection<Greeting> list = service.findAll();
        Assert.assertEquals("failure - expected size", TOTAL_GREETINGS - 1, list.size());
        Greeting deletedEntity = service.findOne(id);
        Assert.assertNull("failure - expected null", deletedEntity);
    }
}
