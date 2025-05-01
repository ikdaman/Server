package com.ikdaman.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * 400 BAD_REQUEST
     */
    // common(00)
    BAD_REQUEST_BY_VALIDATION(HttpStatus.BAD_REQUEST.value(), 4000001, "유효하지 않은 값입니다."),

    /**
     * 401 Unauthorized
     */
    // Auth(01)

    // Member(02)

    // MyBook(03)

    // Notice(04)



    /**
     * 404 Not found
     */
    // Auth(01)
    INVALID_SOCIAL_ACCESS_TOKEN(HttpStatus.NOT_FOUND.value(), 4040101,"유효하지 않은 Social Access Token 입니다."),

    // Member(02)
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), 4040201, "존재하지 않는 유저입니다."),

    // MyBook(03)

    // Notice(04)



    /**
     * 500 Internal Server Error
     */
    // Common(00)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 5000001, "서버 내부 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 5000002,"데이터베이스 오류가 발생했습니다."),
    NULL_POINTER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), 5000003,"잘못된 값(NULL)이 처리되었습니다."),
    PERSIST_EXCEPTION_TO_THIRD_PARTY(HttpStatus.INTERNAL_SERVER_ERROR.value(), 5000004,"외부 저장소에 저장을 실패했습니다.");

    private final int status;
    private final int code;
    private final String message;

}
