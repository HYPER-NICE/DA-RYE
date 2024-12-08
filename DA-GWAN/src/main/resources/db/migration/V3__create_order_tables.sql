-- V3__create_order_tables.sql
-- 생성 날짜: 2024-12-08
-- 설명: 주문 및 주문 상세 테이블, 상태 테이블 및 결제 관련 테이블 생성

-- =======================
-- 주문 상태 테이블 생성
-- =======================
CREATE TABLE order_status (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 상태 ID (기본 키)',
                              name VARCHAR(50) NOT NULL UNIQUE COMMENT '주문 상태 이름',
                              description VARCHAR(255) NULL COMMENT '주문 상태 설명',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 주문 상태 데이터 삽입
INSERT INTO order_status (name, description)
VALUES
    -- 결제 상태
    ('PAYMENT_PENDING', '결제 대기'),             -- 결제 진행 전
    ('PAYMENT_COMPLETED', '결제 완료'),            -- 결제 성공

    -- 취소 상태
    ('CANCELLATION_REQUESTED', '취소 요청 대기'), -- 사용자가 취소 요청
    ('CANCELLATION_COMPLETED', '취소 완료'),      -- 주문 취소 처리 완료

    -- 환불 상태
    ('REFUND_REQUESTED', '환불 요청 대기'),       -- 환불 요청 상태
    ('REFUNDED', '환불 완료');                    -- 환불 완료

-- =======================
-- 배송 상태 테이블 생성
-- =======================
CREATE TABLE delivery_status (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '배송 상태 ID (기본 키)',
                                 name VARCHAR(50) NOT NULL UNIQUE COMMENT '배송 상태 이름',
                                 description VARCHAR(255) NULL COMMENT '배송 상태 설명',
                                 created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                 last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                 deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 기본 배송 상태 데이터 삽입
INSERT INTO delivery_status (name, description)
VALUES
    -- 배송 프로세스
    ('PREPARING_SHIPMENT', '배송 준비 중'),          -- 배송 준비 상태
    ('SHIPPED', '배송 중'),                         -- 배송이 진행 중
    ('DELIVERED', '배송 완료'),                     -- 고객에게 배송이 완료된 상태

    -- 수거/환불 프로세스
    ('RETURN_PREPARING', '수거 준비 중'),           -- 수거 준비 상태
    ('RETURN_PICKUP', '수거 중'),                   -- 수거 진행 상태
    ('RETURN_COMPLETED', '수거 완료');              -- 수거가 완료된 상태


-- =======================
-- 주문 테이블 생성
-- =======================
CREATE TABLE member_order (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 ID (기본 키)',
                              member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                              total_price INT NOT NULL COMMENT '실제 결제 금액',
                              order_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '주문 날짜',
                              delivery_address VARCHAR(255) NOT NULL COMMENT '배송 주소',

                              delivery_status_id BIGINT NOT NULL COMMENT '배송 상태 ID (외래 키)',
                              order_status_id BIGINT NOT NULL COMMENT '주문 상태 ID (외래 키)',

                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- =======================
-- 주문 상세 테이블 생성
-- =======================
CREATE TABLE member_order_detail (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 상세 ID (기본 키)',
                                     order_id BIGINT NOT NULL COMMENT '주문 ID (외래 키)',
                                     product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                                     amount INT NOT NULL COMMENT '구매 수량',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- =======================
-- 결제 수단 테이블 생성
-- =======================
CREATE TABLE payment_method (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 수단 ID (기본 키)',
                                name VARCHAR(50) NOT NULL UNIQUE COMMENT '결제 수단 이름 (예: 카드, 계좌이체, 포인트, 쿠폰)',
                                description VARCHAR(255) NULL COMMENT '결제 수단 설명',
                                created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 기본 결제 수단 데이터 삽입
INSERT INTO payment_method (name, description)
VALUES
    ('CREDIT_CARD', '신용 카드 결제'),
    ('BANK_TRANSFER', '계좌이체');

-- =======================
-- 결제 내역 테이블 생성
-- =======================
CREATE TABLE payment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 내역 ID (기본 키)',
                         order_id BIGINT NOT NULL COMMENT '주문 ID (외래 키)',
                         payment_method_id BIGINT NOT NULL COMMENT '결제 수단 ID (외래 키)',
                         amount INT NOT NULL COMMENT '해당 수단으로 결제한 금액',
                         payment_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '결제 날짜',
                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                         deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);