package com.zerobase.storereservation.common.exception;

import com.zerobase.storereservation.common.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    // 프로젝트 내에서 커스텀 예외를 처리하기 위한 클래스.
    // RuntimeException 을 상속받아 예외가 발생하면 ErrorCode 기반으로 예외 처리
    private final ErrorCode errorCode;
}
