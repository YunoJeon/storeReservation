package com.zerobase.storereservation.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    // 예외 발생 시 클라이언트에 반환할 응답의 형식을 정의.
    // ErrorCode 에서 정의한 코드와 메세지를 포함하여 전달
    private String code;
    private String message;
}
