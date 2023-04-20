package com.aqualen.minikubeconfluentkafkaspring;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@EnableKafka
@SpringBootApplication
public class MinikubeConfluentKafkaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinikubeConfluentKafkaSpringApplication.class, args);
    }

    @Bean
    NewTopic mainTopic() {
        return new NewTopic("main", 1, (short) 1);
    }
}

@Component
@RequiredArgsConstructor
class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @EventListener(ApplicationStartedEvent.class)
    public void produce() {
        kafkaTemplate.send("main", "Code for life").whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Successfully sent record with offset = " + result.getRecordMetadata().offset() +
                        " and partition = " + result.getRecordMetadata().partition() + " to Kafka");
            } else {
                System.err.println("Unable to send message due to : " + ex.getMessage());
            }
        });

    }
}

@Component
class Consumer {
    @KafkaListener(id = "main", topics = {"main"})
    public void consumer(ConsumerRecord<String, String> simpleRecord) {
        System.out.println("Successfully received record with offset = " + simpleRecord.offset() +
                " and partition = " + simpleRecord.partition() + " from Kafka: "
                + simpleRecord.key() + ", " + simpleRecord.value());
    }
}
