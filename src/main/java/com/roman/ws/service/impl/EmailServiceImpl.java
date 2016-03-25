package com.roman.ws.service.impl;

import com.roman.ws.model.Greeting;
import com.roman.ws.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * The EmailServiceBean implements all business behaviors defined by the
 * EmailService interface.
 *
 * Created by Administrator on 3/25/16.
 */
@Service
public class EmailServiceImpl implements EmailService {
    /**
     * The Logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public Boolean send(Greeting greeting) {
        logger.info("> send");

        Boolean success = Boolean.FALSE;

        // Simulate method execution time
        long pause = 5000;
        try {
            Thread.sleep(pause);
        } catch (Exception e) {
            // do nothing
        }
        logger.info("Processing time was {} seconds.", pause / 1000);

        success = Boolean.TRUE;

        logger.info("< send");
        return success;
    }

    @Async
    @Override
    public void sendAsync(Greeting greeting) {
        logger.info("> sendAsync");

        try {
            send(greeting);
        } catch (Exception e) {
            logger.warn("Exception caught sending asynchronous mail.", e);
        }

        logger.info("< sendAsync");
    }

    @Async
    @Override
    public CompletableFuture<Boolean> sendAsyncWithResult(Greeting greeting) {
        logger.info("> sendAsyncWithCompletableFutureResult");

        CompletableFuture<Boolean> response = new CompletableFuture<Boolean>();

        try {
            Boolean success = send(greeting);
            response.complete(success);
        } catch (Exception e) {
            logger.warn("Exception caught sending asynchronous mail.", e);
            response.completeExceptionally(e);
        }

        logger.info("< sendAsyncWithCompletableFutureResult");
        return response;
    }
}
