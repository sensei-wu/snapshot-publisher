package com.zen.lab.zidane.snapshot.publisher.infra;

public class KafkaProperties {

    private KafkaProperties() {}

    public static final String TOPIC = "zidane-snapshot-queue";
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int CONNECTION_TIMEOUT = 5000;

}
