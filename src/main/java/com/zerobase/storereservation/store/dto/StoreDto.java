package com.zerobase.storereservation.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StoreDto {

    private Long id;
    private String storeName;
    private String storeLocation;
}
