package com.smartcase.dispute.workflow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import com.smartcase.dispute.model.ClassifiedDispute;

@Configuration
@EnableKafka
public class WorkflowConfig {

    private final KafkaProperties kafkaProperties;

    public WorkflowConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, ClassifiedDispute> consumerFactory() {
        JsonDeserializer<ClassifiedDispute> deserializer = new JsonDeserializer<>(ClassifiedDispute.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("com.smartcase.dispute.*");
        deserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(
                kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClassifiedDispute> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ClassifiedDispute> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
