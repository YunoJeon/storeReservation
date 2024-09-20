package com.zerobase.storereservation.reservation.repository;

import com.zerobase.storereservation.reservation.entity.Reservation;
import com.zerobase.storereservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByStoreAndReservationTime(Store store, LocalDateTime reservationTime);
}
