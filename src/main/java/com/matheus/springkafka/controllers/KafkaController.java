package com.matheus.springkafka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.springkafka.dtos.KafkaDTO;

@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, KafkaDTO> kafkaTemplate;
    
    @PostMapping("/kafka")
    public ResponseEntity<KafkaDTO> kafka(@RequestBody KafkaDTO kdto) {
        kafkaTemplate.send("kafka", kdto);
        return ResponseEntity.ok().body(kdto);
    }

    @KafkaListener(id = "receiver-api", containerFactory = "kafkaDtoKafkaListenerContainerFactory",         
        topicPartitions =
        { 
            @TopicPartition(topic = "kafka", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")),
        }
    )

    public void kafkaDTOListener(KafkaDTO message) {
        System.out.println("Received Message in dto: " + message.getName());
    }
}
