package com.matheus.springkafka.dtos;

public class KafkaDTO {
    
    private String name;
    private int age;

    public KafkaDTO() {}

    public KafkaDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
