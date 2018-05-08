package com.zen.lab.zidane.snapshot.publisher.service;

public interface EbetPublisher {
    /**
     * Publishes the given snapshot to the remote service
     * @param snapshotMessage message to be published
     * @return HTTP return code (success/error)
     */
    int publish(String snapshotMessage);
}
