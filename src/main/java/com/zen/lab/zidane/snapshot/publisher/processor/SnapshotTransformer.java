package com.zen.lab.zidane.snapshot.publisher.consumer;

import com.zen.lab.zidane.snapshot.publisher.infra.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

public class SnapshotTransformer {

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "zidane-snapshot-publisher");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 2000); //this parameter is important to control the frequency of update to internal queue

        KStreamBuilder builder = new KStreamBuilder();

        KStream<String, String> snapshotStream = builder.stream("zidane-snapshot-queue");

        snapshotStream.to("zidane-snapshot-internal"); //This queue should be a log compacted queue for optimal space management. But otherwise it has no impact on behaviour

        KTable<String, String> snapshotTable = builder.table("zidane-snapshot-internal");

        snapshotTable.toStream().foreach((key, value) ->
            {
                System.out.println("Publishing to ebet " + value);
            }
            );

        KafkaStreams streams = new KafkaStreams(builder, config);
        // only do this in dev - not in prod
        streams.cleanUp();
        streams.start();

        //shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        /*while(true){
            System.out.println(streams.toString());
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                break;
            }
        }*/
    }
}
