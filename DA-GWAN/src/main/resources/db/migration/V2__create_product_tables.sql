-- 카테고리 테이블 생성
CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID (기본 키)',
                          name VARCHAR(255) NOT NULL UNIQUE COMMENT '카테고리 이름',
                          parent_id BIGINT NULL COMMENT '부모 카테고리 ID',
                          created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                          last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                          deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 카테고리 히스토리 테이블
CREATE TABLE category_history (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 이력 ID (기본 키)',
                                  category_id BIGINT NOT NULL COMMENT '원래 카테고리 ID',
                                  name VARCHAR(255) NOT NULL COMMENT '카테고리 이름(이전 데이터)',
                                  parent_id BIGINT NULL COMMENT '부모 카테고리 ID(이전 데이터)',
                                  created_date DATETIME(6) NULL COMMENT '카테고리 원래 생성 날짜(이전 데이터)',
                                  last_modified_date DATETIME(6) NULL COMMENT '카테고리 원래 수정 날짜(이전 데이터)',
                                  deleted_date DATETIME(6) NULL COMMENT '카테고리 원래 삭제 날짜(이전 데이터)',
                                  operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형 (UPDATE 또는 DELETE)',
                                  history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'
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

-- 제품 히스토리 테이블 생성
CREATE TABLE product_history (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 이력 ID (기본 키)',
                                 product_id BIGINT NOT NULL COMMENT '원래 제품 ID',
                                 name VARCHAR(255) NOT NULL COMMENT '제품 이름(이전 데이터)',
                                 description TEXT NULL COMMENT '제품 설명(이전 데이터)',
                                 price INT NOT NULL COMMENT '판매 가격(이전 데이터)',
                                 category_id BIGINT NOT NULL COMMENT '카테고리 ID(이전 데이터)',
                                 created_date DATETIME(6) NULL COMMENT '제품 원래 생성 날짜(이전 데이터)',
                                 last_modified_date DATETIME(6) NULL COMMENT '제품 원래 수정 날짜(이전 데이터)',
                                 deleted_date DATETIME(6) NULL COMMENT '제품 원래 삭제 날짜(이전 데이터)',
                                 operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형 (UPDATE 또는 DELETE)',
                                 history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'
);

-- 입고 메인 테이블
CREATE TABLE inbound_main (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '입고 메인 ID (기본 키)',
                              reference_code VARCHAR(255) NOT NULL UNIQUE COMMENT '참조 코드 (입고 기준)',
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
                                purchase_price INT NOT NULL COMMENT '입고 가격 (원)',
                                expiry_date DATETIME(6) NOT NULL COMMENT '유통기한',
                                created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',

                                CONSTRAINT FK_inbound_detail_main FOREIGN KEY (inbound_main_id) REFERENCES inbound_main (id) ON DELETE CASCADE,
                                CONSTRAINT FK_inbound_detail_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
                                CHECK (quantity > 0)
);


-- 출고 메인 테이블
CREATE TABLE outbound_main (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '출고 메인 ID (기본 키)',
                               reference_code VARCHAR(255) NOT NULL UNIQUE COMMENT '참조 코드 (출고 기준)',
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

                                 CONSTRAINT FK_outbound_detail_main FOREIGN KEY (outbound_main_id) REFERENCES outbound_main (id) ON DELETE CASCADE,
                                 CONSTRAINT FK_outbound_detail_inbound FOREIGN KEY (inbound_detail_id) REFERENCES inbound_detail (id) ON DELETE CASCADE,
                                 CONSTRAINT FK_outbound_detail_reason FOREIGN KEY (reason_id) REFERENCES outbound_reason (id) ON DELETE CASCADE,
                                 CHECK (quantity > 0)
);

-- 입고 사유 테이블 생성
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

-- 기본 입고 사유 데이터 삽입
INSERT INTO inbound_reason (name, description)
VALUES
    ('PURCHASE', '공급업체로부터 신규 구매로 인한 입고'),
    ('CUSTOMER_RETURN', '고객 반품 상품 재입고'),
    ('PROMOTIONAL_EVENT', '프로모션 목적 추가 재고 확보로 인한 입고'),
    ('CORRECTION', '재고 조정(오류 정정)으로 인한 입고');

-- 기본 출고 사유 데이터 삽입
INSERT INTO outbound_reason (name, description)
VALUES
    ('DAMAGED', '상품 파손으로 인한 폐기 출고'),
    ('DONATION', '기부를 위한 재고 출고'),
    ('TRANSFER_OUT', '외부 창고나 다른 지점으로의 재고 이동 출고'),
    ('INTERNAL_USE', '사내 용도 사용(촬영, 교육)으로 인한 출고');




