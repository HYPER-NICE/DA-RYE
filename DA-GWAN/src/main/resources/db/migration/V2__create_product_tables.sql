-- =========================================
-- 2. 입고/출고 관련 코드 테이블
-- =========================================

-- 입고 코드 테이블
CREATE TABLE INBOUND_CODE
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 코드 ID (기본 키)',
    NAME               VARCHAR(255) NOT NULL UNIQUE COMMENT '입고 코드 이름',
    DESCRIPTION        VARCHAR(255) NULL COMMENT '입고 코드 설명',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 출고 코드 테이블
CREATE TABLE OUTBOUND_CODE
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 코드 ID (기본 키)',
    NAME               VARCHAR(255) NOT NULL UNIQUE COMMENT '출고 코드 이름',
    DESCRIPTION        VARCHAR(255) NULL COMMENT '출고 코드 설명',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE INBOUND_MAIN
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 메인 ID (기본 키)',
    RECEIVED_DATE      DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '입고 날짜',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE INBOUND_DETAIL
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 디테일 ID (기본 키)',
    INBOUND_MAIN_ID    BIGINT       NOT NULL COMMENT '입고 메인 ID (외래 키)',
    PRODUCT_ID         BIGINT       NOT NULL COMMENT '제품 ID (외래 키)',
    BATCH_NUMBER       VARCHAR(255) NOT NULL COMMENT '배치 번호',
    QUANTITY           INT          NOT NULL COMMENT '입고 수량',
    PURCHASE_PRICE     INT          NOT NULL COMMENT '입고 가격',
    EXPIRY_DATE        DATETIME(6)  NOT NULL COMMENT '유통기한',
    INBOUND_CODE_ID    BIGINT       NOT NULL COMMENT '입고 코드 ID (외래 키)',
    INBOUND_LOCATION   VARCHAR(255) NULL COMMENT '입고 위치',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
    CHECK (QUANTITY > 0),
    CONSTRAINT FK_INBOUND_DETAIL_MAIN FOREIGN KEY (INBOUND_MAIN_ID) REFERENCES INBOUND_MAIN (ID) ON DELETE CASCADE,
    CONSTRAINT FK_INBOUND_DETAIL_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID) ON DELETE CASCADE,
    CONSTRAINT FK_INBOUND_DETAIL_INBOUND FOREIGN KEY (INBOUND_CODE_ID) REFERENCES INBOUND_CODE (ID) ON DELETE CASCADE
);

CREATE TABLE OUTBOUND_MAIN
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 메인 ID (기본 키)',
    OUTBOUND_DATE      DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '출고 날짜',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE OUTBOUND_DETAIL
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 디테일 ID (기본 키)',
    OUTBOUND_MAIN_ID   BIGINT  NOT NULL COMMENT '출고 메인 ID (외래 키)',
    INBOUND_DETAIL_ID  BIGINT  NOT NULL COMMENT '입고 디테일 ID (외래 키)',
    QUANTITY           INT     NOT NULL COMMENT '출고 수량',
    OUTBOUND_CODE_ID   BIGINT  NOT NULL COMMENT '출고 코드 ID (외래 키)',
    IS_PROCESSED       BOOLEAN NOT NULL COMMENT '처리 여부',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
    CHECK (QUANTITY > 0),
    CONSTRAINT FK_OUTBOUND_DETAIL_MAIN FOREIGN KEY (OUTBOUND_MAIN_ID) REFERENCES OUTBOUND_MAIN (ID) ON DELETE CASCADE,
    CONSTRAINT FK_OUTBOUND_DETAIL_INBOUND FOREIGN KEY (INBOUND_DETAIL_ID) REFERENCES INBOUND_DETAIL (ID) ON DELETE CASCADE,
    CONSTRAINT FK_OUTBOUND_DETAIL_OUTBOUND FOREIGN KEY (OUTBOUND_CODE_ID) REFERENCES OUTBOUND_CODE (ID) ON DELETE CASCADE
);

CREATE TABLE STOCK
(
    ID                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '재고 ID (기본 키)',
    PRODUCT_ID         BIGINT       NOT NULL COMMENT '제품 ID (외래 키)',
    CURRENT_STOCK      BIGINT       NOT NULL COMMENT '현재 재고 수량',
    BATCH_NUMBER       VARCHAR(255) NOT NULL COMMENT '배치 번호',
    CREATED_DATE       DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE       DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
    CONSTRAINT FK_PRODUCT_ID_STOCK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID)
);

CREATE TABLE CART
(
    MEMBER_ID               BIGINT                PRIMARY KEY        NOT NULL COMMENT '회원 ID (기본 키, 외래 키)',
    PRODUCT_ID              BIGINT                                   NOT NULL COMMENT '제품 ID (외래 키)',
    ORDER_QUANTITY          INT         DEFAULT 1                    NOT NULL COMMENT '주문 수량',
    TOTAL_PRODUCT_DISCOUNT  INT                                      NULL COMMENT '상품별 총 할인 금액',
    TOTAL_PRODUCT_PRICE     INT                                      NULL COMMENT '상품별 총 금액',
    PRODUCT_EARNING_POINTS  INT                                      NULL COMMENT '상품별 적립 포인트',
    REDEEMED_POINTS         INT         DEFAULT 0                    NOT NULL COMMENT '사용한 적립 포인트',
    CREATED_DATE            DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NULL COMMENT '생성 날짜',
    LAST_MODIFIED_DATE      DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    CONSTRAINT FK_CART_ITEM_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID),
    CONSTRAINT FK_CART_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID) ON DELETE CASCADE,
    CHECK (`ORDER_QUANTITY` > 0)
);