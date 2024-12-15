CREATE TABLE MEMBER_GRADE_POLICY
(
    -- 기본키
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고객 등급 ID',

    -- 데이터
    GRADE_NAME         VARCHAR(50)  NOT NULL COMMENT '고객 등급 이름',
    MIN_AMOUNT         INT          NOT NULL COMMENT '최소 구매 금액',
    MAX_AMOUNT         INT          NOT NULL COMMENT '최대 구매 금액',
    PERIOD_DAYS        INT          NOT NULL COMMENT '등급 기준 기간(일)',
    DESCRIPTION        VARCHAR(255) NULL COMMENT '고객 등급 설명',
    CONSTRAINT CHK_MIN_LESS_THAN_MAX CHECK (MIN_AMOUNT <= MAX_AMOUNT),

    --  시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE MEMBER_GRADE_POLICY_HISTORY
(
    -- 기본키
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고객 등급 이력 ID (기본 키)',

    -- 외래키
    GRADE_ID             BIGINT       NOT NULL COMMENT '원래 고객 등급 ID',
    CONSTRAINT FK_MEMBER_GRADE_POLICY_HISTORY_GRADE
        FOREIGN KEY (GRADE_ID) REFERENCES MEMBER_GRADE_POLICY (ID) ON UPDATE CASCADE ON DELETE RESTRICT,

    -- 데이터
    GRADE_NAME           VARCHAR(50)  NOT NULL COMMENT '고객 등급 이름(이전 데이터)',
    MIN_AMOUNT           INT          NOT NULL COMMENT '최소 구매 금액(이전 데이터)',
    MAX_AMOUNT           INT          NOT NULL COMMENT '최대 구매 금액(이전 데이터)',
    PERIOD_DAYS          INT          NOT NULL COMMENT '고객 등급 기간(이전 데이터)',
    DESCRIPTION          VARCHAR(255) NULL COMMENT '고객 등급 설명(이전 데이터)',
    CREATED_DATE         DATETIME(6)  NULL COMMENT '고객 등급 생성 날짜(이전 데이터)',
    LAST_MODIFIED_DATE   DATETIME(6)  NULL COMMENT '고객 등급 수정 날짜(이전 데이터)',
    DELETED_DATE         DATETIME(6)  NULL COMMENT '고객 등급 삭제 날짜(이전 데이터)',

    -- 시스템 컬럼
    OPERATION_TYPE       VARCHAR(10)  NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
    HISTORY_CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'

);

CREATE TABLE MEMBER
(
    -- 기본키
    ID                    BIGINT       NOT NULL PRIMARY KEY COMMENT '회원 ID (기본 키)',

    -- 외래키
    GRADE_ID              BIGINT       NULL COMMENT '고객 등급 ID (외래 키)',
    CONSTRAINT FK_MEMBER_GRADE_POLICY FOREIGN KEY (GRADE_ID) REFERENCES MEMBER_GRADE_POLICY (ID),

    -- 데이터
    NAME                  VARCHAR(255) NOT NULL COMMENT '회원 이름',
    SEX                   CHAR(1)      NOT NULL COMMENT '성별',
    BIRTHDATE             DATE         NOT NULL COMMENT '생년월일',
    EMAIL                 VARCHAR(255) NOT NULL UNIQUE COMMENT '회원 이메일',
    PASSWORD              VARCHAR(255) NOT NULL COMMENT '비밀번호',
    ADDRESS               VARCHAR(255) NULL COMMENT '주소',
    ADDRESS_LINE          VARCHAR(255) NULL COMMENT '상세주소',
    ADDRESS_ZIP_CODE      VARCHAR(255) NULL COMMENT '우편번호',
    MOBILE                VARCHAR(255) NOT NULL COMMENT '휴대폰 번호',
    LANDLINE              VARCHAR(255) NULL COMMENT '전화번호',
    CURRENT_POINTS        INT          NOT NULL DEFAULT 0 COMMENT '현재 보유 포인트',
    TOTAL_EARNED_POINTS   INT          NOT NULL DEFAULT 0 COMMENT '총 얻은 포인트',
    TOTAL_REDEEMED_POINTS INT          NOT NULL DEFAULT 0 COMMENT '총 사용한 포인트',
    MEMBER_STATUS         VARCHAR(255) NOT NULL COMMENT '회원 상태',
    LATEST_LOGIN_DATE     DATETIME     NULL COMMENT '마지막 로그인 날짜',
    REG_DATE              DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '회원가입 날짜',
    CHECK (SEX IN ('M', 'F', 'O')),

    -- 시스템 컬럼
    CREATED_DATE          DATETIME(6)           DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE    DATETIME(6)           DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE          DATETIME(6)           DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE DELIVERY_ADDRESS
(
    -- 기본키
    ID                        BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '배송지 주소 ID (기본 키)',

    -- 외래키
    MEMBER_ID                 BIGINT       NOT NULL COMMENT '회원 ID (외래 키)',
    CONSTRAINT FK_DELIVERY_ADDRESS_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID) ON UPDATE CASCADE ON DELETE RESTRICT ,

    -- 데이터
    DELIVERY_ADDRESS          VARCHAR(255) NOT NULL COMMENT '배송지 주소',
    DELIVERY_ADDRESS_LINE     VARCHAR(255) NOT NULL COMMENT '배송지 상세주소',
    DELIVERY_ADDRESS_ZIP_CODE INT          NOT NULL COMMENT '배송지 우편번호',
    ADDRESS_TYPE              VARCHAR(255) NOT NULL COMMENT '주소 별명',
    IS_PRIMARY_ADDRESS        BOOLEAN      NOT NULL COMMENT '기본배송지 여부',
    IS_LATEST_ADDRESS         BOOLEAN      NOT NULL COMMENT '최근배송지 여부',

    -- 시스템 컬럼
    CREATED_DATE              DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE        DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE              DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE MEMBER_HISTORY
(
    -- 기본키
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '멤버 이력 ID (기본 키)',

    -- 외래키
    MEMBER_ID            BIGINT       NOT NULL COMMENT '이력 대상 회원 ID',
    CONSTRAINT FK_MEMBER_HISTORY_MEMBER
        FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID) ON UPDATE CASCADE ON DELETE RESTRICT,

    -- 데이터
    NAME                 VARCHAR(255) NOT NULL COMMENT '회원 이름(이전 데이터)',
    EMAIL                VARCHAR(255) NOT NULL COMMENT '회원 이메일(이전 데이터)',
    PASSWORD             VARCHAR(255) NOT NULL COMMENT '비밀번호(이전 데이터)',
    ADDRESS              VARCHAR(255) NULL COMMENT '주소(이전 데이터)',
    MOBILE               VARCHAR(255) NULL COMMENT '휴대폰(이전 데이터)',
    GRADE_ID             BIGINT       NULL COMMENT '등급 ID(이전 데이터)',
    CREATED_DATE         DATETIME(6)  NULL COMMENT '회원 생성 날짜(이전 데이터)',
    LAST_MODIFIED_DATE   DATETIME(6)  NULL COMMENT '회원 수정 날짜(이전 데이터)',
    DELETED_DATE         DATETIME(6)  NULL COMMENT '회원 삭제 날짜(이전 데이터)',

    -- 시스템 컬럼
    OPERATION_TYPE       VARCHAR(10)  NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
    HISTORY_CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'

);

-- 카테고리 테이블
CREATE TABLE CATEGORY
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID (기본 키)',
    NAME               VARCHAR(255) NOT NULL UNIQUE COMMENT '카테고리 이름',
    PARENT_ID          BIGINT       NULL COMMENT '부모 카테고리 ID (외래 키)',
    SCREEN_ORDER       BIGINT       NOT NULL COMMENT '화면에 보여질 정렬 순서',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
    CONSTRAINT FK_CATEGORY_PARENT FOREIGN KEY (PARENT_ID) REFERENCES CATEGORY (ID)
);

-- 카테고리 이력 테이블
CREATE TABLE CATEGORY_HISTORY
(
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 이력 ID (기본 키)',
    CATEGORY_ID          BIGINT       NOT NULL COMMENT '원래 카테고리 ID (외래 키)',
    NAME                 VARCHAR(255) NOT NULL COMMENT '카테고리 이름(이전)',
    PARENT_ID            BIGINT       NULL COMMENT '부모 카테고리 ID(이전)',
    CREATED_DATE         DATETIME(6)  NULL COMMENT '카테고리 원래 생성 날짜(이전)',
    LAST_MODIFIED_DATE   DATETIME(6)  NULL COMMENT '카테고리 원래 수정 날짜(이전)',
    DELETED_DATE         DATETIME(6)  NULL COMMENT '카테고리 원래 삭제 날짜(이전)',
    OPERATION_TYPE       VARCHAR(10)  NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
    HISTORY_CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
    CONSTRAINT FK_CATEGORY_HISTORY_CATEGORY
        FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (ID) ON DELETE CASCADE
);

-- 공통 코드 테이블
CREATE TABLE COMMON_CODE
(
    -- 기본키
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '공통 코드 ID (기본 키)',

    -- 대체키
    CODE        VARCHAR(5) NOT NULL UNIQUE COMMENT '상태 코드',
    NAME        VARCHAR(255) NOT NULL UNIQUE COMMENT '상태 이름',

    -- 데이터
    DESCRIPTION VARCHAR(255) NOT NULL COMMENT '상태 설명',

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 상품 테이블
CREATE TABLE PRODUCT
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '상품 ID (기본 키)',
    NAME               VARCHAR(255)          NOT NULL COMMENT '상품 이름',
    SHORT_DESCRIPTION  TEXT                  NULL COMMENT '상품 간단 설명',
    LONG_DESCRIPTION   TEXT                  NULL COMMENT '상품 상세 설명',
    MAIN_DESCRIPTION   VARCHAR(1000)         NULL COMMENT '상품 본문 내용',
    PRICE              INT         DEFAULT 0 NOT NULL COMMENT '판매 가격',
    CATEGORY_ID        BIGINT                NOT NULL COMMENT '카테고리 ID (외래 키)',
    IMPORTER           VARCHAR(255)          NULL COMMENT '수입원',
    MANUFACTURER       VARCHAR(255)          NULL COMMENT '제조원',
    EXPIRATION_DATE    DATETIME(6)           NOT NULL COMMENT '소비기한',
    SIZE               VARCHAR(255)          NULL COMMENT '사이즈',
    CAPACITY           VARCHAR(255)          NULL COMMENT '용량(수량)',
    INGREDIENTS        VARCHAR(255)          NULL COMMENT '원재료명 및 성분',
    PRECAUTIONS        VARCHAR(255)          NULL COMMENT '주의사항',
    SALE_DATE          DATETIME(6)           NOT NULL COMMENT '판매시작일',
    REGISTRATION_DATE  DATETIME(6)           NOT NULL COMMENT '상품등록일',
    NEW                BOOLEAN     DEFAULT FALSE COMMENT '신상품',
    HOT                BOOLEAN     DEFAULT FALSE COMMENT '최근 인기 상품',
    BEST               BOOLEAN     DEFAULT FALSE COMMENT '인기 상품',
    COMMON_CODE_ID     BIGINT       NOT NULL COMMENT '공통 코드 ID (외래 키)',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
    CHECK (PRICE >= 0),
    CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (ID),
    CONSTRAINT FK_PRODUCT_COMMON_CODE FOREIGN KEY (COMMON_CODE_ID) REFERENCES COMMON_CODE (ID)
);

-- 상품 이력 테이블
CREATE TABLE PRODUCT_HISTORY
(
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '상품 이력 ID (기본 키)',
    PRODUCT_ID           BIGINT       NOT NULL COMMENT '원래 상품 ID (외래 키)',
#     NAME                 VARCHAR(255) NOT NULL COMMENT '상품 이름(이전)',
#     DESCRIPTION          TEXT         NULL COMMENT '상품 설명(이전)',
    PRICE                INT          NOT NULL COMMENT '판매 가격(이전)',
    CATEGORY_ID          BIGINT       NOT NULL COMMENT '카테고리 ID(이전)',
    OPERATION_TYPE       VARCHAR(10)  NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
    HISTORY_CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
    CREATED_DATE         DATETIME(6)  NULL COMMENT '제품 원래 생성 날짜(이전)',
    LAST_MODIFIED_DATE   DATETIME(6)  NULL COMMENT '제품 원래 수정 날짜(이전)',
    DELETED_DATE         DATETIME(6)  NULL COMMENT '제품 원래 삭제 날짜(이전)',
    CONSTRAINT FK_PRODUCT_HISTORY_PRODUCT
        FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID) ON DELETE CASCADE
);

-- 가격 이력 테이블
CREATE TABLE PRICE_HISTORY
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '가격 이력 ID (기본 키)',
    PRODUCT_ID         BIGINT      NOT NULL COMMENT '원래 상품 ID(외래 키)',
    PRICE              INT         NOT NULL COMMENT '할인 가격',
    CHANGE_START_DATE  DATETIME(6) NULL COMMENT '변경 시작 일자',
    CHANGE_END_DATE    DATETIME(6) NULL COMMENT '변경 종료 일자',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
    CONSTRAINT FK_PRODUCT_ID FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID)
);