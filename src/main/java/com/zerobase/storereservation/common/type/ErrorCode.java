package com.zerobase.storereservation.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

// 커스텀 예외처리 정의. 직관적으로 알 수 있는 code 와 message 작성
public enum ErrorCode {

    NOT_FOUND_USER("NOT_FOUND_USER", "사용자를 찾을 수 없습니다."),
    NOT_FOUND_STORE("NOT_FOUND_STORE", "매장을 찾을 수 없습니다."),
    NOT_FOUND_REVIEW("NOT_FOUND_REVIEW", "리뷰를 찾을 수 없습니다."),
    NOT_FOUND_RESERVATION("NOT_FOUND_RESERVATION", "예약 내역을 찾을 수 없습니다."),
    NOT_CHECKED_RESERVATION("NOT_CHECKED_RESERVATION", "예약이 확인되지 않았습니다."),
    ACCESS_DENIED("ACCESS_DENIED", "접근 권한이 없습니다."),
    FAILED_LONGIN("FAILED_LONGIN", "로그인에 실패하였습니다."),
    ALREADY_EXISTS_USER("ALREADY_EXISTS_USER", "등록된 계정이 존재합니다."),
    ALREADY_RESERVATION("ALREADY_RESERVATION", "이미 해당 시간에 예약이 있습니다."),
    INVALID_REQUEST("INVALID_REQUEST", "잘못된 요청입니다."),
    EARLY_TIME("EARLY_TIME", "방문 확인이 가능한 시간이 아닙니다. 예약 10분 전에 다시 확인해주세요."),
    BEFORE_RESERVATION("BEFORE_RESERVATION", "예약 시간이 지났으므로 방문 확인이 불가능합니다. 직원에게 문의주세요"),
    NOT_VISITED("NOT_VISITED", "방문 이후에 리뷰를 작성할 수 있습니다."),
    EXISTS_REVIEW("EXISTS_REVIEW", "등록된 리뷰가 있어 삭제할 수 없습니다.");

    private final String code;
    private final String message;
}
