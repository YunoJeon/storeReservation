package com.zerobase.storereservation.store.service;

import com.zerobase.storereservation.common.exception.CustomException;
import com.zerobase.storereservation.review.repository.ReviewRepository;
import com.zerobase.storereservation.store.dto.StoreDetailDto;
import com.zerobase.storereservation.store.dto.StoreDto;
import com.zerobase.storereservation.store.entity.Store;
import com.zerobase.storereservation.store.repository.StoreRepository;
import com.zerobase.storereservation.user.entity.User;
import com.zerobase.storereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.zerobase.storereservation.common.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    // 매장 관리자(파트너)가 매장 등록
    public StoreDetailDto createStore(Store store, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new CustomException(NOT_FOUND_USER);
        }

        User user = optionalUser.get();

        // 파트너가 아니면 매장 등록 불가능
        if (!user.isRole()) {
            throw new CustomException(ACCESS_DENIED);
        }

        store =  storeRepository.save(Store.builder()
                .storeName(store.getStoreName())
                .storeLocation(store.getStoreLocation())
                .storeDescription(store.getStoreDescription())
                .user(user)
                .build());

        StoreDetailDto dto = new StoreDetailDto();
        dto.setId(store.getId());
        dto.setUserId(store.getUser().getId());
        dto.setStoreName(store.getStoreName());
        dto.setStoreLocation(store.getStoreLocation());
        dto.setStoreDescription(store.getStoreDescription());

        return dto;
    }

    // 매장 수정(파트너만 가능)
    public StoreDetailDto updateStore(Store store, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new CustomException(NOT_FOUND_USER);
        }

        User user = optionalUser.get();

        // 파트너가 아니면 접근 거부
        if (!user.isRole()) {
            throw new CustomException(ACCESS_DENIED);
        }

        Store existsStore = storeRepository.findById(store.getId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_STORE));

        // 수정하려는 매장(storeId) 의 등록자(userId)와 수정자(userId)가 다르면 접근 거부
        if (!existsStore.getUser().getId().equals(userId)) {
            throw new CustomException(ACCESS_DENIED);
        }

        existsStore.setStoreName(store.getStoreName());
        existsStore.setStoreLocation(store.getStoreLocation());
        existsStore.setStoreDescription(store.getStoreDescription());

        Store updateStore = storeRepository.save(existsStore);

        StoreDetailDto dto = new StoreDetailDto();
        dto.setId(updateStore.getId());
        dto.setStoreName(updateStore.getStoreName());
        dto.setStoreLocation(updateStore.getStoreLocation());
        dto.setStoreDescription(updateStore.getStoreDescription());
        dto.setUserId(updateStore.getUser().getId());

        return dto;
    }

    // 매장 삭제(파트너만 가능)
    public void deleteStore(Long storeId, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new CustomException(NOT_FOUND_USER);
        }

        User user = optionalUser.get();

        // 파트너가 아니면 접근 거부
        if (!user.isRole()) {
            throw new CustomException(ACCESS_DENIED);
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_STORE));

        // 삭제하려는 매장(storeId)의 등록자(userId) 와 삭제하려는 사용자(userId) 가 다르면 접근 거부
        if (!store.getUser().getId().equals(userId)) {
            throw new CustomException(ACCESS_DENIED);
        }

        // 해당 매장에 등록된 리뷰가 있는지 확인
        boolean hasReview = reviewRepository.existsByStoreId(storeId);

        // 리뷰가 등록이 되어있으면 삭제 불가능.(리뷰를 먼저 전체 삭제한 후 매장 삭제 가능)
        if (hasReview) {
            throw new CustomException(EXISTS_REVIEW);
        }

        storeRepository.deleteById(storeId);
    }

    // 모든 사용자가 볼수있는 매장 전체 리스트
    public List<StoreDto> getAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(store -> new StoreDto(
                        store.getId(),
                        store.getStoreName(),
                        store.getStoreLocation()))
                .collect(Collectors.toList());
    }

    // 매장 전체 리스트에서 선택해서 들어가서 볼 수 있는 매장 상세 정보
    public StoreDetailDto getStoreById(Long id) {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_STORE));

        return new StoreDetailDto(
                store.getId(),
                store.getUser().getId(),
                store.getStoreName(),
                store.getStoreLocation(),
                store.getStoreDescription()
        );
    }
}
