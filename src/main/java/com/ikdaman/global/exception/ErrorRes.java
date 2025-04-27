package com.ikdaman.global.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * 에러 응답 DTO
 */
@Getter
@Builder
public class ErrorRes {
    private final int status;       // HTTP 상태 코드
    private final String code;      // 에러 고유 코드 (조합 : 상태 코드 + 도메인코드 + 에러 넘버(01 ~ 99))
    private final String message;   // 에러 메시지
}
