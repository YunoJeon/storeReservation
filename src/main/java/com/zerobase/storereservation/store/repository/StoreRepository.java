package com.zerobase.storereservation.store.repository;

import com.zerobase.storereservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
