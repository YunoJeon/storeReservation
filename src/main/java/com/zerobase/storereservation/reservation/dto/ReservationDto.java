package com.zerobase.storereservation.reservation.dto;

import com.zerobase.storereservation.reservation.type.StatusType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDto {

    private Long id;
    private Long storeId;
    private String storeName;
    private LocalDateTime reservationTime;
    private StatusType status;
}
