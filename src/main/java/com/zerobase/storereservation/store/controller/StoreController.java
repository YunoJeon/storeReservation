package com.zerobase.storereservation.store.controller;

import com.zerobase.storereservation.store.dto.StoreDetailDto;
import com.zerobase.storereservation.store.dto.StoreDto;
import com.zerobase.storereservation.store.entity.Store;
import com.zerobase.storereservation.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // http://localhost:8080/store?userId=(userId)
    @Operation(summary = "매장 등록", description = "매장 등록 API 입니다. user_role 이 true 인 계정만 등록 가능합니다.")
    @PostMapping
    public ResponseEntity<StoreDetailDto> createStore(
            @RequestBody Store storeEntity,
            @RequestParam Long userId) {
        StoreDetailDto createStore = storeService.createStore(storeEntity, userId);
        return ResponseEntity.ok(createStore);
    }

    // http://localhost:8080/store/(storeId)?userId=(userId)
    @Operation(summary = "매장 수정", description = "매장 수정 API 입니다. user_role 이 true 인 계정이고, 매장 등록한 계정만 수정 가능합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<StoreDetailDto> updateStore(
            @PathVariable Long id,
            @RequestBody Store store,
            @RequestParam Long userId
    ) {
        store.setId(id);
        StoreDetailDto updateStore = storeService.updateStore(store, userId);
        return ResponseEntity.ok(updateStore);
    }

    // http://localhost:8080/store/(storeId)?userId=(userId)
    @Operation(summary = "매장 삭제", description = "매장 삭제 API 입니다. user_role 이 true 인 계정이고, 매장 등록한 계정만 삭제 가능합니다. 추가적으로 해당 매장으로 등록된 리뷰가 있으면 삭제가 불가능합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        storeService.deleteStore(id, userId);
        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
    }

    // http://localhost:8080/store
    @Operation(summary = "매장 검색", description = "매장 검색 API 입니다. 매장 PK, 매장명, 매장위치가 반환됩니다.")
    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStores() {
        List<StoreDto> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    // http://localhost:8080/store/(storeId)
    @Operation(summary = "매장 디테일", description = "매장 디테일 API 입니다. 매장 PK, 매장 등록 유저 ID, 매장명, 매장 위치, 매장 상세설명이 반환됩니다.")
    @GetMapping("/{id}")
    public ResponseEntity<StoreDetailDto> getDetailStore(@PathVariable Long id) {
        StoreDetailDto store = storeService.getStoreById(id);
        return ResponseEntity.ok(store);
    }
}
