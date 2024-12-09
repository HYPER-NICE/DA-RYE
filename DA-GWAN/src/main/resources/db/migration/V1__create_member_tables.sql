-- =========================================
-- 1. 등급, 멤버, 카테고리, 상품 관련 테이블
-- =========================================

CREATE TABLE member_grade_policy (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '등급 ID (기본 키)',
                                     grade_name VARCHAR(50) NOT NULL COMMENT '등급 이름',
                                     min_amount INT NOT NULL COMMENT '최소 구매 금액',
                                     max_amount INT NOT NULL COMMENT '최대 구매 금액',
                                     period_days INT NOT NULL COMMENT '등급 기준 기간(일)',
                                     description VARCHAR(255) NULL COMMENT '등급 설명',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                     CONSTRAINT chk_min_less_than_max CHECK (min_amount <= max_amount)
);

CREATE TABLE member_grade_policy_history (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '등급 이력 ID (기본 키)',
                                             grade_id BIGINT NOT NULL COMMENT '원래 등급 ID',
                                             grade_name VARCHAR(50) NOT NULL COMMENT '등급 이름(이전 데이터)',
                                             min_amount INT NOT NULL COMMENT '최소 구매 금액(이전 데이터)',
                                             max_amount INT NOT NULL COMMENT '최대 구매 금액(이전 데이터)',
                                             period_days INT NOT NULL COMMENT '등급 기간(이전 데이터)',
                                             description VARCHAR(255) NULL COMMENT '등급 설명(이전 데이터)',
                                             created_date DATETIME(6) NULL COMMENT '등급 원래 생성 날짜(이전)',
                                             last_modified_date DATETIME(6) NULL COMMENT '등급 원래 수정 날짜(이전)',
                                             deleted_date DATETIME(6) NULL COMMENT '등급 원래 삭제 날짜(이전)',
                                             operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                             history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'
);

CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '회원 ID (기본 키)',
                        name VARCHAR(255) NOT NULL COMMENT '회원 이름',
                        email VARCHAR(255) NOT NULL UNIQUE COMMENT '회원 이메일',
                        password VARCHAR(255) NOT NULL COMMENT '비밀번호',
                        address VARCHAR(255) NULL COMMENT '주소',
                        mobile VARCHAR(255) NULL COMMENT '휴대폰 번호',
                        grade_id BIGINT NULL COMMENT '등급 ID (외래 키)',
                        created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                        last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                        deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                        CONSTRAINT FK_member_grade_policy FOREIGN KEY (grade_id) REFERENCES member_grade_policy (id)
);

CREATE TABLE member_history (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '멤버 이력 ID (기본 키)',
                                member_id BIGINT NOT NULL COMMENT '이력 대상 회원 ID',
                                name VARCHAR(255) NOT NULL COMMENT '회원 이름(이전 데이터)',
                                email VARCHAR(255) NOT NULL COMMENT '회원 이메일(이전 데이터)',
                                password VARCHAR(255) NOT NULL COMMENT '비밀번호(이전 데이터)',
                                address VARCHAR(255) NULL COMMENT '주소(이전 데이터)',
                                mobile VARCHAR(255) NULL COMMENT '휴대폰(이전 데이터)',
                                grade_id BIGINT NULL COMMENT '등급 ID(이전 데이터)',
                                created_date DATETIME(6) NULL COMMENT '회원 원래 생성 날짜(이전)',
                                last_modified_date DATETIME(6) NULL COMMENT '회원 원래 수정 날짜(이전)',
                                deleted_date DATETIME(6) NULL COMMENT '회원 원래 삭제 날짜(이전)',
                                operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'
);

CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID (기본 키)',
                          name VARCHAR(255) NOT NULL UNIQUE COMMENT '카테고리 이름',
                          parent_id BIGINT NULL COMMENT '부모 카테고리 ID',
                          created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                          last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                          deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                          CONSTRAINT FK_category_parent FOREIGN KEY (parent_id) REFERENCES category (id)
);

CREATE TABLE category_history (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 이력 ID (기본 키)',
                                  category_id BIGINT NOT NULL COMMENT '원래 카테고리 ID',
                                  name VARCHAR(255) NOT NULL COMMENT '카테고리 이름(이전)',
                                  parent_id BIGINT NULL COMMENT '부모 카테고리 ID(이전)',
                                  created_date DATETIME(6) NULL COMMENT '카테고리 원래 생성 날짜(이전)',
                                  last_modified_date DATETIME(6) NULL COMMENT '카테고리 원래 수정 날짜(이전)',
                                  deleted_date DATETIME(6) NULL COMMENT '카테고리 원래 삭제 날짜(이전)',
                                  operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                  history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 ID (기본 키)',
                         name VARCHAR(255) NOT NULL COMMENT '제품 이름',
                         description TEXT NULL COMMENT '제품 설명',
                         price INT DEFAULT 0 NOT NULL COMMENT '판매 가격',
                         category_id BIGINT NOT NULL COMMENT '카테고리 ID (외래 키)',
                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                         deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                         CHECK (price >= 0),
                         CONSTRAINT FK_product_category FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE product_history (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 이력 ID (기본 키)',
                                 product_id BIGINT NOT NULL COMMENT '원래 제품 ID',
                                 name VARCHAR(255) NOT NULL COMMENT '제품 이름(이전)',
                                 description TEXT NULL COMMENT '제품 설명(이전)',
                                 price INT NOT NULL COMMENT '판매 가격(이전)',
                                 category_id BIGINT NOT NULL COMMENT '카테고리 ID(이전)',
                                 created_date DATETIME(6) NULL COMMENT '제품 원래 생성 날짜(이전)',
                                 last_modified_date DATETIME(6) NULL COMMENT '제품 원래 수정 날짜(이전)',
                                 deleted_date DATETIME(6) NULL COMMENT '제품 원래 삭제 날짜(이전)',
                                 operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                 history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'
);