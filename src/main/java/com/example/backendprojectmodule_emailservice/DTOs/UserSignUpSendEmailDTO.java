package com.example.backendprojectmodule_emailservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpSendEmailDTO {
    private String sendTo;
    private String sendFrom;
    private String subject;
    private String body;
}
