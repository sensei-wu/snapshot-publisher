package com.zen.lab.zidane.snapshot.publisher.infra;

import com.zen.lab.zidane.snapshot.publisher.service.EbetPublisher;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaStreamsConfig {

    private KafkaProperties kafkaProperties;
    private EbetPublisher ebetPublisher;

    @Autowired
    public KafkaStreamsConfig(KafkaProperties kafkaProperties, EbetPublisher ebetPublisher) {
        this.kafkaProperties = kafkaProperties;
        this.ebetPublisher = ebetPublisher;
    }

    @Bean("ZidanePublisherTopology")
    public TopologyBuilder createTopologyBuilder() {
        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> snapshotStream = builder.stream(kafkaProperties.getInputTopic());
        snapshotStream.to(kafkaProperties.getInternalTopic());
        //This queue should be a log compacted queue for optimal space management. But otherwise it has no impact on behaviour
        KTable<String, String> snapshotTable = builder.table(kafkaProperties.getInternalTopic());
        snapshotTable.toStream().foreach((fixtureId, snapshotMessage) -> ebetPublisher.publish(snapshotMessage));
        return builder;
    }
}
