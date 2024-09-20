package com.zerobase.storereservation.reservation.entity;

import com.zerobase.storereservation.reservation.type.StatusType;
import com.zerobase.storereservation.store.entity.Store;
import com.zerobase.storereservation.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    private StatusType status;
}
