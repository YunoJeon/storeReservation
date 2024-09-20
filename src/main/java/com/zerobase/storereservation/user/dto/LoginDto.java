package com.zerobase.storereservation.user.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String phoneNumber;
    private String password;
}
