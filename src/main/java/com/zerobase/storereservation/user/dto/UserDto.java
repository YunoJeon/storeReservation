package com.zerobase.storereservation.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long id;

    private String phoneNumber;

    private String userName;

    private boolean role;
}
