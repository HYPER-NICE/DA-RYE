-- =========================================
-- 2. 입고/출고 관련 코드 테이블
-- =========================================

-- 입고 코드 테이블
CREATE TABLE inbound_code (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 코드 ID (기본 키)',
                              name VARCHAR(255) NOT NULL UNIQUE COMMENT '입고 코드 이름',
                              description VARCHAR(255) NULL COMMENT '입고 코드 설명',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 출고 코드 테이블
CREATE TABLE outbound_code (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 코드 ID (기본 키)',
                               name VARCHAR(255) NOT NULL UNIQUE COMMENT '출고 코드 이름',
                               description VARCHAR(255) NULL COMMENT '출고 코드 설명',
                               created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                               last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                               deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE inbound_main (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 메인 ID (기본 키)',
                              received_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '입고 날짜',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE inbound_detail (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 디테일 ID (기본 키)',
                                inbound_main_id BIGINT NOT NULL COMMENT '입고 메인 ID (외래 키)',
                                product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                                batch_number VARCHAR(255) NOT NULL COMMENT '배치 번호',
                                quantity INT NOT NULL COMMENT '입고 수량',
                                purchase_price INT NOT NULL COMMENT '입고 가격',
                                expiry_date DATETIME(6) NOT NULL COMMENT '유통기한',
                                inbound_code_id BIGINT NOT NULL COMMENT '입고 코드 ID (외래 키)',
                                inbound_location VARCHAR(255) NULL COMMENT '입고 위치',
                                created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                CHECK (quantity > 0),
                                CONSTRAINT FK_inbound_detail_main FOREIGN KEY (inbound_main_id) REFERENCES inbound_main (id) ON DELETE CASCADE,
                                CONSTRAINT FK_inbound_detail_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
                                CONSTRAINT FK_inbound_detail_inbound FOREIGN KEY (inbound_code_id) REFERENCES inbound_code (id) ON DELETE CASCADE
);

CREATE TABLE outbound_main (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 메인 ID (기본 키)',
                               outbound_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '출고 날짜',
                               created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                               last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                               deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE outbound_detail (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 디테일 ID (기본 키)',
                                 outbound_main_id BIGINT NOT NULL COMMENT '출고 메인 ID (외래 키)',
                                 inbound_detail_id BIGINT NOT NULL COMMENT '입고 디테일 ID (외래 키)',
                                 quantity INT NOT NULL COMMENT '출고 수량',
                                 outbound_code_id BIGINT NOT NULL COMMENT '출고 코드 ID (외래 키)',
                                 is_processed BOOLEAN NOT NULL COMMENT '처리 여부',
                                 created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                 last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                 deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                 CHECK (quantity > 0),
                                 CONSTRAINT FK_outbound_detail_main FOREIGN KEY (outbound_main_id) REFERENCES outbound_main (id) ON DELETE CASCADE,
                                 CONSTRAINT FK_outbound_detail_inbound FOREIGN KEY (inbound_detail_id) REFERENCES inbound_detail (id) ON DELETE CASCADE,
                                 CONSTRAINT FK_outbound_detail_outbound FOREIGN KEY (outbound_code_id) REFERENCES outbound_code (id) ON DELETE CASCADE
);

CREATE TABLE outbound_main (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 메인 ID (기본 키)',
                               outbound_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '출고 날짜',
                               created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                               last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                               deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE stock (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '재고 ID (기본 키)',
                               product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                               current_stock BIGINT NOT NULL COMMENT '현재 재고 수량',
                               batch_number VARCHAR(255) NOT NULL COMMENT '배치 번호',
                               created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                               last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                               deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);