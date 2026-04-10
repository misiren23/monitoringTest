package com.example.monitoringTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DataService {

    private final List<String> idCollection = new CopyOnWriteArrayList<>();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "test-topic";

    public synchronized void saveId(String id){
        idCollection.add(id);

        if(idCollection.size() >= 100){
            addToKafka();
        }
    }
/*
    public List<String> getAllId(){
        return new ArrayList<>(idCollection);
    }
*/
    private void addToKafka(){
        String message = String.join(",", idCollection);
        kafkaTemplate.send(TOPIC, message);
        idCollection.clear();
    }
}
