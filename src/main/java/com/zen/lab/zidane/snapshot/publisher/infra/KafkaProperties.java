package com.zen.lab.zidane.snapshot.publisher.infra;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "snapshot-publisher.kafka-streams")
public class KafkaProperties {

    private String applicationIdConfig;
    private String autoOffsetResetConfig;
    private String commitIntervalMsConfig;
    private String inputTopic;
    private String internalTopic;
    private String bootstrapServers;

    public String getApplicationIdConfig() {
        return applicationIdConfig;
    }

    public void setApplicationIdConfig(String applicationIdConfig) {
        this.applicationIdConfig = applicationIdConfig;
    }

    public String getAutoOffsetResetConfig() {
        return autoOffsetResetConfig;
    }

    public void setAutoOffsetResetConfig(String autoOffsetResetConfig) {
        this.autoOffsetResetConfig = autoOffsetResetConfig;
    }

    public String getCommitIntervalMsConfig() {
        return commitIntervalMsConfig;
    }

    public void setCommitIntervalMsConfig(String commitIntervalMsConfig) {
        this.commitIntervalMsConfig = commitIntervalMsConfig;
    }

    public String getInputTopic() {
        return inputTopic;
    }

    public void setInputTopic(String inputTopic) {
        this.inputTopic = inputTopic;
    }

    public String getInternalTopic() {
        return internalTopic;
    }

    public void setInternalTopic(String internalTopic) {
        this.internalTopic = internalTopic;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public StreamsConfig createStreamsConfig() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, this.applicationIdConfig);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.autoOffsetResetConfig);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, this.commitIntervalMsConfig); //this parameter is important to control the frequency of update to internal queue
        return new StreamsConfig(config);
    }
}
