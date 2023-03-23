package com.matheus.springkafka.controllers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.springkafka.dtos.UserDTO;

@RestController
public class UserController {

    @Autowired
    private KafkaTemplate<String, UserDTO> kafkaTemplate;
    
    @PostMapping("/user")
    public ResponseEntity<UserDTO> kafka(@RequestBody UserDTO kdto) {
        kafkaTemplate.send("user", kdto);
        return ResponseEntity.ok().body(kdto);
    }

    @KafkaListener(id = "app-user", containerFactory = "UserDTOKafkaListenerContainerFactory",         
        topicPartitions =
        { 
            @TopicPartition(topic = "user", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")),
        }
    )

    public void userDTOListener(ConsumerRecord<String, UserDTO> record) {
        System.out.println("Received Message in dto: " + record.value().getName());
    }
}
