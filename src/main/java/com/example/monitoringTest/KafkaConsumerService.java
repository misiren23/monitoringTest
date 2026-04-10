package com.example.monitoringTest;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KafkaConsumerService {

    private final List<String> consumedMessages = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "test-topic", groupId = "data-consumer-group")
    public void consume(List<ConsumerRecord<String, String>> records, Acknowledgment acknowledgment) {

        for (ConsumerRecord<String, String> record : records) {
            String message = record.value();

            if (message != null && !message.isEmpty()) {
                String[] ids = message.split(",");

                consumedMessages.addAll(Arrays.asList(ids));
            }
        }
        acknowledgment.acknowledge();
    }

    public List<String> getAndClearMessages() {
        List<String> messages = new ArrayList<>(consumedMessages);
        consumedMessages.clear();
        return messages;
    }
}
