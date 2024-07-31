package umc.SukBakJi.global.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.SukBakJi.global.apiPayload.code.BaseErrorCode;
import umc.SukBakJi.global.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER4002", "이미 존재하는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER4003", "잘못된 비밀번호입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "요청한 리소스를 찾을 수 없습니다."),

    // 예시
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

    // For test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

    // FoodCategory Error
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "FOOD_CATEGORY4001", "음식 카테고리가 없습니다."),

    // 데이터베이스 관련 에러
    DATABASE_CONNECTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "DB5001", "데이터베이스 연결에 실패했습니다."),
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB5002", "SQL 쿼리 실행 중 오류가 발생했습니다."),

    // 연구 주제 관련 에러
    RESEARCH_NOT_FOUND(HttpStatus.NOT_FOUND, "TOPIC4001", "연구 주제를 찾을 수 없습니다."),

    // 연구실 후기 관련 에러
    LAB_REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "LAB_REVIEW4041", "연구실 후기를 찾을 수 없습니다."),
    LAB_NOT_FOUND(HttpStatus.NOT_FOUND, "LAB4041", "연구실을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND_FOR_REVIEW(HttpStatus.NOT_FOUND, "MEMBER4042", "후기를 작성할 사용자를 찾을 수 없습니다."),
    INVALID_REVIEW_CONTENT(HttpStatus.BAD_REQUEST, "REVIEW4001", "후기 내용이 유효하지 않습니다."),
    DUPLICATE_REVIEW(HttpStatus.CONFLICT, "REVIEW4091", "중복된 후기가 존재합니다."),
    UNAUTHORIZED_REVIEW_ACCESS(HttpStatus.FORBIDDEN, "REVIEW4031", "해당 후기에 접근할 권한이 없습니다."),
    PROFESSOR_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFESSOR4041", "해당 지도교수를 찾을 수 없습니다."),
    INVALID_PROFESSOR_NAME(HttpStatus.NOT_FOUND, "PROFESSOR4042", "지도교수 이름을 찾을 수 없습니다."),

    // 알람 설정 관련 에러
    INVALID_DATE(HttpStatus.BAD_REQUEST, "ALARM400", "올바르지 않는 날짜입니다."),
    DUPLICATE_ALARM_NAME(HttpStatus.BAD_REQUEST, "ALARM401", "중복된 알람 이름입니다."),
    INVALID_ALARM(HttpStatus.BAD_REQUEST, "ALARM402", "유효하지 않은 알람입니다."),

    // 대학교 설정 관련 에러
    INVALID_UNIVERSITY(HttpStatus.BAD_REQUEST, "UNIVERSITY400", "유효하지 않은 학교입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
