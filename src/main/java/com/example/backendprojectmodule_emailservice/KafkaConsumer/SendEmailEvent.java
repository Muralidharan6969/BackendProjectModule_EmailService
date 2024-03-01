package com.example.backendprojectmodule_emailservice.KafkaConsumer;

import com.example.backendprojectmodule_emailservice.DTOs.UserSignUpSendEmailDTO;
import com.example.backendprojectmodule_emailservice.Utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class SendEmailEvent {
    private ObjectMapper objectMapper;

    @Autowired
    public SendEmailEvent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sendEmail", groupId = "EmailService")
    public void sendEmailtoUser(String message) throws JsonProcessingException {
        UserSignUpSendEmailDTO sendEmailDTO = objectMapper.readValue(message, UserSignUpSendEmailDTO.class);
        String fromEmail = "pkmmurali6969@gmail.com"; //requires valid gmail id
        final String password = "rireonighmkreqce"; // correct password for gmail id
        final String toEmail = sendEmailDTO.getSendTo(); // can be any email id

//        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail, sendEmailDTO.getSubject(), sendEmailDTO.getBody());
    }
}
