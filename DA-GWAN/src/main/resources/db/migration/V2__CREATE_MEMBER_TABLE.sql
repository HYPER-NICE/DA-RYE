CREATE TABLE MEMBER
(
    -- 기본키
    ID                   BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '회원 ID (기본 키)',

    -- 대체키
    EMAIL                VARCHAR(255)          NOT NULL UNIQUE COMMENT '회원 이메일',

    -- 데이터
    PASSWORD             VARCHAR(255)          NOT NULL COMMENT '비밀번호',
    NAME                 VARCHAR(50)           NOT NULL COMMENT '회원 이름',
    SEX                  CHAR(1)               NOT NULL COMMENT '성별',
    BIRTHDATE            DATE                  NOT NULL COMMENT '생년월일',
    MOBILE               VARCHAR(13)           NOT NULL COMMENT '휴대폰 번호',
    POINT                INT                   NOT NULL DEFAULT 0 COMMENT '현재 보유 포인트',
    LOCKED               BOOLEAN               NOT NULL DEFAULT FALSE COMMENT '계정 잠금 여부',
    PW_FAILED_COUNT      INT                   NOT NULL DEFAULT 0 COMMENT '비밀번호 실패 횟수',
    LATEST_LOGIN_DATE    DATETIME              NULL COMMENT '마지막 로그인 날짜',
    REG_DATE             DATETIME(6)           NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '가입 날짜',
    CHECK (SEX IN ('M', 'F', 'O')),

    -- 시스템 관리 컬럼
    CREATED_DATE         DATETIME(6)                    DEFAULT CURRENT_TIMESTAMP(6) COMMENT '레코드 생성 날짜 및 시간',
    LAST_MODIFIED_DATE   DATETIME(6)                    DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '마지막 수정된 날짜 및 시간',
    LAST_MODIFIED_MEMBER BIGINT                NULL COMMENT '레코드를 마지막으로 수정한 회원 ID',
    DELETED_DATE         DATETIME(6)                    DEFAULT NULL COMMENT '레코드가 삭제된 날짜 (논리 삭제)',
    CONSTRAINT FK_MEMBER_LAST_MODIFIED_MEMBER FOREIGN KEY (LAST_MODIFIED_MEMBER) REFERENCES MEMBER (ID) ON UPDATE CASCADE ON DELETE RESTRICT
);