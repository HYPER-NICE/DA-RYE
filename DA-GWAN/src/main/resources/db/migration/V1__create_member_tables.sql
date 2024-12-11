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
                                             history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                             CONSTRAINT FK_member_grade_policy_history_grade
                                                 FOREIGN KEY (grade_id) REFERENCES member_grade_policy (id) ON DELETE CASCADE
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
                                history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                CONSTRAINT FK_member_history_member
                                    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);

CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID (기본 키)',
                          name VARCHAR(255) NOT NULL UNIQUE COMMENT '카테고리 이름',
                          parent_id BIGINT NULL COMMENT '부모 카테고리 ID',
                          screen_order BIGINT NOT NULL COMMENT '화면에 보여질 정렬 순서',
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
                                  history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                  CONSTRAINT FK_category_history_category
                                      FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);

CREATE TABLE product_status_code (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '상품 상태 코드 ID (기본 키)',
                                     name VARCHAR(255) NOT NULL UNIQUE COMMENT '상품 상태 코드 이름',
                                     description VARCHAR(255) NULL COMMENT '상품 상태 코드 설명',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 ID (기본 키)',
                         name VARCHAR(255) NOT NULL COMMENT '제품 이름',
                         short_description TEXT NULL COMMENT '제품 간단 설명',
                         long_description TEXT NULL COMMENT '제품 상세 설명',
                         main_description VARCHAR(1000) NULL COMMENT '제품 본문 내용',
                         price INT DEFAULT 0 NOT NULL COMMENT '판매 가격',
                         category_id BIGINT NOT NULL COMMENT '카테고리 ID (외래 키)',
                         status_id BIGINT NOT NULL COMMENT '상품 상태 ID (외래 키)',
                         importer VARCHAR(255) NULL COMMENT '수입원',
                         manufacturer VARCHAR(255) NULL COMMENT '제조원',
                         expiration_date DATETIME(6) NOT NULL COMMENT '소비기한',
                         size VARCHAR(255) NULL COMMENT '사이즈',
                         capacity VARCHAR(255) NULL COMMENT '용량(수량)',
                         ingredients VARCHAR(255) NULL COMMENT '원재료명 및 성분',
                         precautions VARCHAR(255) NULL COMMENT '주의사항',
                         sale_date DATETIME(6) NOT NULL COMMENT '판매시작일',
                         new BOOLEAN DEFAULT FALSE COMMENT '신상품',
                         hot BOOLEAN DEFAULT FALSE COMMENT '최근 인기 상품',
                         best BOOLEAN DEFAULT FALSE COMMENT '인기 상품',
                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                         deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                         CHECK (price >= 0),
                         CONSTRAINT FK_product_category FOREIGN KEY (category_id) REFERENCES category (id),
                         CONSTRAINT FK_product_status_code FOREIGN KEY (status_id) REFERENCES product_status_code (id)
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
                                 history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                 CONSTRAINT FK_product_history_product
                                     FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

CREATE TABLE price_history (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '가격 이력 ID (기본 키)',
                                     product_id BIGINT NOT NULL COMMENT '원래 상품 ID(외래 키)',
                                     price INT NOT NULL COMMENT '판매 가격',
                                     change_start_date DATETIME(6) NULL COMMENT '변경 시작 일자',
                                     change_end_date DATETIME(6) NULL COMMENT '변경 종료 일자',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                     CONSTRAINT FK_product_id FOREIGN KEY (product_id) REFERENCES product (id)
);