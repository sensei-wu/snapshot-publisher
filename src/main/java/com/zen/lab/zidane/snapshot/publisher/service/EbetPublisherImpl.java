package com.zen.lab.zidane.snapshot.publisher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EbetPublisherImpl implements EbetPublisher {

    Logger LOGGER = LoggerFactory.getLogger(EbetPublisher.class);

    @Override
    public int publish(String snapshotMessage) {
        //add 2 sec latency
        try {
            LOGGER.info("Publishing to ebet " + snapshotMessage);
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            return 500;
        }
        return 200;
    }
}
