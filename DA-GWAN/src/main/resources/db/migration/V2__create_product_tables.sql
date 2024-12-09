-- =========================================
-- 2. 입고/출고 관련 테이블 (사유 먼저 생성)
-- =========================================

-- 입고 사유 테이블
CREATE TABLE inbound_reason (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 사유 ID (기본 키)',
                                name VARCHAR(255) NOT NULL UNIQUE COMMENT '입고 사유 이름',
                                description VARCHAR(255) NULL COMMENT '입고 사유 설명',
                                created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
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

-- 입고 메인 테이블
CREATE TABLE inbound_main (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 메인 ID (기본 키)',
                              reference_code VARCHAR(255) NOT NULL UNIQUE COMMENT '참조 코드',
                              total_quantity INT NOT NULL COMMENT '총 입고 수량',
                              total_cost INT NOT NULL COMMENT '총 입고 비용',
                              received_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '입고 날짜',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 입고 디테일 테이블
CREATE TABLE inbound_detail (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 디테일 ID (기본 키)',
                                inbound_main_id BIGINT NOT NULL COMMENT '입고 메인 ID (외래 키)',
                                product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                                batch_number VARCHAR(255) NOT NULL COMMENT '배치 번호',
                                quantity INT NOT NULL COMMENT '입고 수량',
                                purchase_price INT NOT NULL COMMENT '입고 가격',
                                expiry_date DATETIME(6) NOT NULL COMMENT '유통기한',
                                created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                CHECK (quantity > 0),
                                CONSTRAINT FK_inbound_detail_main FOREIGN KEY (inbound_main_id) REFERENCES inbound_main (id) ON DELETE CASCADE,
                                CONSTRAINT FK_inbound_detail_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

-- 출고 메인 테이블
CREATE TABLE outbound_main (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 메인 ID (기본 키)',
                               reference_code VARCHAR(255) NOT NULL UNIQUE COMMENT '참조 코드',
                               total_quantity INT NOT NULL COMMENT '총 출고 수량',
                               total_cost INT NOT NULL COMMENT '총 출고 비용',
                               outbound_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '출고 날짜',
                               created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                               last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                               deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 출고 디테일 테이블
CREATE TABLE outbound_detail (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 디테일 ID (기본 키)',
                                 outbound_main_id BIGINT NOT NULL COMMENT '출고 메인 ID (외래 키)',
                                 inbound_detail_id BIGINT NOT NULL COMMENT '입고 디테일 ID (외래 키)',
                                 quantity INT NOT NULL COMMENT '출고 수량',
                                 reason_id BIGINT NOT NULL COMMENT '출고 사유 ID (외래 키)',
                                 created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                 last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                 deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                 CHECK (quantity > 0),
                                 CONSTRAINT FK_outbound_detail_main FOREIGN KEY (outbound_main_id) REFERENCES outbound_main (id) ON DELETE CASCADE,
                                 CONSTRAINT FK_outbound_detail_inbound FOREIGN KEY (inbound_detail_id) REFERENCES inbound_detail (id) ON DELETE CASCADE,
                                 CONSTRAINT FK_outbound_detail_reason FOREIGN KEY (reason_id) REFERENCES outbound_reason (id) ON DELETE CASCADE
);
