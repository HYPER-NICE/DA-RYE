-- =========================================
-- 시스템 기본 테이블
-- 의존성이 없는 코드성 상태성 테이블을 작성합니다.
-- =========================================

CREATE TABLE COMMON_CODE
(
    -- 기본키
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '공통 코드 ID (기본 키)',

    -- 대체키
    CODE        VARCHAR(5) NOT NULL UNIQUE COMMENT '상태 코드',

    -- 데이터
    NAME        VARCHAR(255) NOT NULL COMMENT '상태 이름',
    DESCRIPTION VARCHAR(255) NOT NULL COMMENT '상태 설명',

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);