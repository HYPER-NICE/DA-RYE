-- =========================================
-- 카트 테이블 및 카트 아이템 테이블
-- =========================================

CREATE TABLE CART
(
    MEMBER_ID           BIGINT PRIMARY KEY NOT NULL COMMENT '회원 ID (기본 키, 외래 키)',
    PRODUCT_ID          BIGINT             NOT NULL COMMENT '제품 ID (외래 키)',
    PRODUCT_NAME        VARCHAR(255)       NOT NULL COMMENT '제품 이름',
    PRODUCT_PRICE       INT                NOT NULL COMMENT '상품 금액',
    PRODUCT_QUANTITY    INT                NOT NULL DEFAULT 1 COMMENT '수량',
    DISCOUNT            INT                NULL COMMENT '할인 금액',
    PRODUCT_TOTAL_PRICE INT                NULL COMMENT '상품별 총 금액',
    EARNING_POINTS      INT                NULL COMMENT '적립 포인트',
    CREATED_DATE        DATETIME(6)                 DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE  DATETIME(6)                 DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    CHECK (PRODUCT_QUANTITY > 0),
    CONSTRAINT FK_CART_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID) ON DELETE CASCADE,
    CONSTRAINT FK_CART_ITEM_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID)
);
