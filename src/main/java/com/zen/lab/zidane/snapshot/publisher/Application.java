package com.zen.lab.zidane.snapshot.publisher;

import com.zen.lab.zidane.snapshot.publisher.infra.KafkaProperties;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final TopologyBuilder topologyBuilder;
    private final KafkaProperties kafkaProperties;

    @Autowired
    public Application(TopologyBuilder topologyBuilder, KafkaProperties kafkaProperties) {
        this.topologyBuilder = topologyBuilder;
        this.kafkaProperties = kafkaProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        KafkaStreams streams = new KafkaStreams(topologyBuilder, kafkaProperties.createStreamsConfig());
        streams.cleanUp(); // only do this in dev - not in prod
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
