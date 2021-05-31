package com.peopleflow.employee.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Value(value = "${kafka.employee.topic}")
    private String employeeTopic;

    @Bean
    public NewTopic employeeTopic() {
        return new NewTopic(employeeTopic, 1, (short) 1);
    }

}
