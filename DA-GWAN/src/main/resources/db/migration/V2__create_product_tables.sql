-- =========================================
-- 2. 입고/출고 관련 코드 테이블
-- =========================================

-- 입고 코드 테이블
CREATE TABLE INBOUND_CODE
(
    -- 기본키
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 코드 ID (기본 키)',

    -- 데이터
    NAME               VARCHAR(255) NOT NULL UNIQUE COMMENT '입고 코드 이름',
    DESCRIPTION        VARCHAR(255) NULL COMMENT '입고 코드 설명',

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 출고 코드 테이블
CREATE TABLE OUTBOUND_CODE
(
    -- 기본키
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 코드 ID (기본 키)',

    -- 데이터
    NAME               VARCHAR(255) NOT NULL UNIQUE COMMENT '출고 코드 이름',
    DESCRIPTION        VARCHAR(255) NULL COMMENT '출고 코드 설명',

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 발주 테이블
CREATE TABLE INBOUND_ORDER
(
    ID                      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '발주 ID (기본 키)',
    ORDER_DATE              DATETIME(6) NOT NULL COMMENT '발주 날짜',
    EXPECTED_INBOUND_DATE   DATETIME(6) NOT NULL COMMENT '예상 입고 날짜',
    STATUS                  VARCHAR(10) NOT NULL COMMENT '발주 상태',
    TOTAL_QUANTITY          BIGINT      NOT NULL COMMENT '총 발주 수량',
    TOTAL_AMOUNT            BIGINT      NOT NULL COMMENT '총 발주 금액',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 입고 메인 테이블
CREATE TABLE INBOUND_MAIN
(
    -- 기본키
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 메인 ID (기본 키)',

    -- 외래키
    INBOUND_ORDER_ID   BIGINT NOT NULL COMMENT '발주 ID (외래 키)',
    CONSTRAINT FK_INBOUND_MAIN_ORDER FOREIGN KEY (INBOUND_ORDER_ID) REFERENCES INBOUND_ORDER (ID) ON DELETE CASCADE,

        -- 데이터
    RECEIVED_DATE      DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '입고 날짜',

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 입고 상세 테이블
CREATE TABLE INBOUND_DETAIL
(
    -- 기본키
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 디테일 ID (기본 키)',

    -- 외래키
    INBOUND_MAIN_ID    BIGINT       NOT NULL COMMENT '입고 메인 ID (외래 키)',
    PRODUCT_ID         BIGINT       NOT NULL COMMENT '제품 ID (외래 키)',
    BATCH_NUMBER       VARCHAR(255) NOT NULL COMMENT '배치 번호',
    COMMON_CODE_ID     BIGINT       NOT NULL COMMENT '공통 코드 ID (외래 키)',
    CONSTRAINT FK_INBOUND_DETAIL_MAIN FOREIGN KEY (INBOUND_MAIN_ID) REFERENCES INBOUND_MAIN (ID) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT FK_INBOUND_DETAIL_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT FK_INBOUND_DETAIL_INBOUND FOREIGN KEY (COMMON_CODE_ID) REFERENCES COMMON_CODE (ID) ON UPDATE CASCADE ON DELETE RESTRICT,

    -- 데이터
    QUANTITY           INT          NOT NULL COMMENT '입고 수량',
    PRICE              INT          NOT NULL COMMENT '입고 가격',
    EXPIRY_DATE        DATETIME(6)  NOT NULL COMMENT '유통기한',
    INBOUND_LOCATION   VARCHAR(255) NULL COMMENT '입고 위치',
    CHECK (QUANTITY > 0),

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 출고 메인 테이블
CREATE TABLE OUTBOUND_MAIN
(
    -- 기본키
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 메인 ID (기본 키)',

    -- 데이터
    OUTBOUND_DATE      DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '출고 날짜',

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE OUTBOUND_DETAIL
(
    -- 기본키
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 디테일 ID (기본 키)',

    -- 외래키
    OUTBOUND_MAIN_ID   BIGINT  NOT NULL COMMENT '출고 메인 ID (외래 키)',
    INBOUND_DETAIL_ID  BIGINT  NOT NULL COMMENT '입고 디테일 ID (외래 키)',
    OUTBOUND_CODE_ID   BIGINT  NOT NULL COMMENT '출고 코드 ID (외래 키)',
    CONSTRAINT FK_OUTBOUND_DETAIL_MAIN FOREIGN KEY (OUTBOUND_MAIN_ID) REFERENCES OUTBOUND_MAIN (ID) ON DELETE CASCADE,
    CONSTRAINT FK_OUTBOUND_DETAIL_INBOUND FOREIGN KEY (INBOUND_DETAIL_ID) REFERENCES INBOUND_DETAIL (ID) ON DELETE CASCADE,
    CONSTRAINT FK_OUTBOUND_DETAIL_OUTBOUND FOREIGN KEY (OUTBOUND_CODE_ID) REFERENCES OUTBOUND_CODE (ID) ON DELETE CASCADE,

    -- 데이터
    QUANTITY           INT     NOT NULL COMMENT '출고 수량',
    IS_PROCESSED       BOOLEAN NOT NULL COMMENT '처리 여부',
    CHECK (QUANTITY > 0),

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 재고 테이블
CREATE TABLE STOCK
(
    -- 기본키
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '재고 ID (기본 키)',

    -- 외래키
    PRODUCT_ID         BIGINT       NOT NULL COMMENT '제품 ID (외래 키)',
    CONSTRAINT FK_PRODUCT_ID_STOCK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID),

    -- 데이터
    CURRENT_STOCK      BIGINT       NOT NULL COMMENT '현재 재고 수량',
    BATCH_NUMBER       VARCHAR(255) NOT NULL COMMENT '배치 번호',

    -- 시스템 컬럼
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE CART
(
    -- 기본키
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '장바구니 ID (기본 키)',

    -- 외래키
    MEMBER_ID            BIGINT                                   NOT NULL COMMENT '회원 ID (외래 키)',
    PRODUCT_ID           BIGINT                                   NOT NULL COMMENT '상품 ID (외래 키)',
    CONSTRAINT FK_CART_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT FK_CART_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID) ON UPDATE CASCADE ON DELETE RESTRICT,

    -- 데이터
    ORDER_QUANTITY       BIGINT      DEFAULT 1                    NOT NULL COMMENT '상품별 주문 수량',
    ADDED_DATE           DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '장바구니에 담은 날짜',

    -- 시스템 컬럼
    CREATED_DATE         DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NULL COMMENT '생성 날짜',
    LAST_MODIFIED_DATE   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE         DATETIME(6) DEFAULT NULL                 NULL COMMENT '삭제 날짜'
);