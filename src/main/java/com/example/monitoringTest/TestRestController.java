package com.example.monitoringTest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestRestController {

    private final DataService dataService;
    private final KafkaConsumerService kafkaConsumerService;

    public TestRestController(DataService dataService, KafkaConsumerService kafkaConsumerService) {
        this.dataService = dataService;
        this.kafkaConsumerService = kafkaConsumerService;
    }


    @PostMapping("/data")
    public ResponseEntity<Void> saveData(@RequestBody DataRequest request) {
        String id = request.getId();
        dataService.saveId(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/data")
    public ResponseEntity<List<String>> getAllData() {
        List<String> kafkaMessages = kafkaConsumerService.getAndClearMessages();
        return ResponseEntity.ok(kafkaMessages);
    }
}
