package com.zerobase.storereservation.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailDto {

    private Long id;
    private Long userId;
    private String storeName;
    private String storeLocation;
    private String storeDescription;
}
