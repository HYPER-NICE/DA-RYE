-- V3__create_order_tables.sql
-- 생성 날짜: 2024-12-08
-- 설명: 주문 및 주문 상세 테이블 생성

-- =======================
-- 테이블 생성
-- =======================

-- 주문 테이블 생성
CREATE TABLE member_order (
                              id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '주문 ID (기본 키)',
                              member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                              total_price INT NOT NULL COMMENT '총 금액',
                              order_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '주문 날짜',
                              delivery_address VARCHAR(255) NOT NULL COMMENT '배송 주소',
                              payment_method VARCHAR(255) NOT NULL COMMENT '결제 수단',
                              delivery_status ENUM('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL COMMENT '배송 상태',
                              status ENUM('NEW', 'PROCESSING', 'COMPLETED', 'CANCELLED') NOT NULL COMMENT '주문 상태',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 주문 상세 테이블 생성
CREATE TABLE member_order_detail (
                                     id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '주문 상세 ID (기본 키)',
                                     order_id BIGINT NOT NULL COMMENT '주문 ID (외래 키)',
                                     product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                                     amount INT NOT NULL COMMENT '구매 수량',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);
