-- =========================================
-- 3. 주문/결제 관련 테이블
-- =========================================

-- 주문 상태 테이블
CREATE TABLE order_status (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 상태 ID (기본 키)',
                              name VARCHAR(50) NOT NULL UNIQUE COMMENT '주문 상태 이름',
                              description VARCHAR(255) NULL COMMENT '주문 상태 설명',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 배송 상태 테이블
CREATE TABLE delivery_status (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '배송 상태 ID (기본 키)',
                                 name VARCHAR(50) NOT NULL UNIQUE COMMENT '배송 상태 이름',
                                 description VARCHAR(255) NULL COMMENT '배송 상태 설명',
                                 created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                 last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                 deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 주문 메인 테이블
CREATE TABLE member_order_main (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 ID (기본 키)',
                                   member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                                   orderer_name VARCHAR(255) NOT NULL COMMENT '주문자 이름',
                                   orderer_contact VARCHAR(50) NOT NULL COMMENT '주문자 연락처',
                                   receiver_name VARCHAR(255) NOT NULL COMMENT '수령인 이름',
                                   receiver_contact VARCHAR(50) NOT NULL COMMENT '수령인 연락처',
                                   delivery_address VARCHAR(255) NOT NULL COMMENT '배송 기본 주소',
                                   delivery_detail_address VARCHAR(255) NULL COMMENT '배송 상세 주소',
                                   tracking_number VARCHAR(50) NULL COMMENT '송장 번호',
                                   delivery_request_note VARCHAR(255) NULL COMMENT '배송 요청 사항',
                                   total_price INT NOT NULL COMMENT '실제 결제 금액',
                                   order_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '주문 날짜',
                                   delivery_status_id BIGINT NOT NULL COMMENT '배송 상태 ID (외래 키)',
                                   order_status_id BIGINT NOT NULL COMMENT '주문 상태 ID (외래 키)',
                                   created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                   last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                   deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                   CONSTRAINT FK_member_order_main_member FOREIGN KEY (member_id) REFERENCES member (id),
                                   CONSTRAINT FK_member_order_main_delivery_status FOREIGN KEY (delivery_status_id) REFERENCES delivery_status (id),
                                   CONSTRAINT FK_member_order_main_order_status FOREIGN KEY (order_status_id) REFERENCES order_status (id)
);

-- 주문 상세 테이블
CREATE TABLE member_order_detail (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 상세 ID (기본 키)',
                                     order_id BIGINT NOT NULL COMMENT '주문 ID (외래 키)',
                                     product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                                     unit_price INT NOT NULL COMMENT '제품 단가',
                                     amount INT NOT NULL COMMENT '구매 수량',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                     CONSTRAINT FK_member_order_detail_order FOREIGN KEY (order_id) REFERENCES member_order_main (id) ON DELETE CASCADE,
                                     CONSTRAINT FK_member_order_detail_product FOREIGN KEY (product_id) REFERENCES product (id)
);

-- 결제 수단 테이블
CREATE TABLE payment_method (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 수단 ID (기본 키)',
                                name VARCHAR(50) NOT NULL UNIQUE COMMENT '결제 수단 이름',
                                description VARCHAR(255) NULL COMMENT '결제 수단 설명',
                                created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 결제 내역 테이블
CREATE TABLE payment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 내역 ID (기본 키)',
                         order_id BIGINT NOT NULL COMMENT '주문 ID (외래 키)',
                         payment_method_id BIGINT NOT NULL COMMENT '결제 수단 ID (외래 키)',
                         amount INT NOT NULL COMMENT '해당 수단으로 결제한 금액',
                         payment_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '결제 날짜',
                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                         deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                         CONSTRAINT FK_payment_order FOREIGN KEY (order_id) REFERENCES member_order_main (id) ON DELETE CASCADE,
                         CONSTRAINT FK_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method (id)
);
