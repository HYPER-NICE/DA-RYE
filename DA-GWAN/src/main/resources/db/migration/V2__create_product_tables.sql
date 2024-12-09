-- V2__create_product_tables.sql
-- 생성 날짜: 2024-12-08
-- 설명: 제품, 카테고리, 입고 테이블 생성 및 트리거 추가

-- =======================
-- 테이블 생성
-- =======================

-- 카테고리 테이블 생성
CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID (기본 키)',
                          name VARCHAR(255) NOT NULL UNIQUE COMMENT '카테고리 이름',
                          parent_id BIGINT NULL COMMENT '부모 카테고리 ID',
                          created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                          last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                          deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 제품 테이블 생성
CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 ID (기본 키)',
                         name VARCHAR(255) NOT NULL COMMENT '제품 이름',
                         description TEXT NULL COMMENT '제품 설명',
                         price INT DEFAULT 0 NOT NULL COMMENT '판매 가격',
                         category_id BIGINT NOT NULL COMMENT '카테고리 ID (외래 키)',
                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                         deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                         CHECK (price >= 0)
);

-- 입고 테이블 생성
CREATE TABLE inbound (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 ID (기본 키)',
                           product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                           batch_number VARCHAR(255) NOT NULL COMMENT '배치 번호',
                           quantity INT NOT NULL COMMENT '입고 수량',
                           purchase_price INT NOT NULL COMMENT '입고 가격 (원)',
                           expiry_date DATETIME(6) NOT NULL COMMENT '유통기한',
                           received_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '입고 날짜',
                           created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                           last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                           deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 출고 테이블 생성
CREATE TABLE outbound (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 ID (기본 키)',
                          inbound_id BIGINT NOT NULL COMMENT '입고 ID (외래 키)',
                          quantity INT NOT NULL COMMENT '출고 수량',
                          outbound_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '출고 날짜',
                          reason_id BIGINT NOT NULL COMMENT '출고 사유 ID (외래 키)',
                          created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                          last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                          deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                          CHECK (quantity > 0)
);

-- 출고 사유 테이블
CREATE TABLE outbound_reason (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 사유 ID (기본 키)',
                                 name VARCHAR(255) NOT NULL UNIQUE COMMENT '출고 사유 이름',
                                 description VARCHAR(255) NULL COMMENT '출고 사유 설명',
                                 created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                 last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                 deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 기본 출고 사유 데이터 삽입
INSERT INTO outbound_reason (name, description)
VALUES
    ('ORDER', '고객 주문'),
    ('EXPIRED', '유통기한 만료');


