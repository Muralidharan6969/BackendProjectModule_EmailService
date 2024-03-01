package com.example.backendprojectmodule_emailservice.KafkaConsumer;

import com.example.backendprojectmodule_emailservice.DTOs.UserSignUpSendEmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SendEmailEvent {
    private ObjectMapper objectMapper;

    @Autowired
    public SendEmailEvent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sendEmail", groupId = "EmailService")
    public void sendEmailtoUser(String message) {
        try {
            UserSignUpSendEmailDTO sendEmailDTO = objectMapper.readValue(message, UserSignUpSendEmailDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
