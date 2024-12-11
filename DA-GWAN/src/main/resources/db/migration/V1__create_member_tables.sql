CREATE TABLE member_grade_policy (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고객 등급 ID (기본 키)',
                                     grade_name VARCHAR(50) NOT NULL COMMENT '고객 등급 이름',
                                     min_amount INT NOT NULL COMMENT '최소 구매 금액',
                                     max_amount INT NOT NULL COMMENT '최대 구매 금액',
                                     period_days INT NOT NULL COMMENT '등급 기준 기간(일)',
                                     description VARCHAR(255) NULL COMMENT '고객 등급 설명',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                     CONSTRAINT chk_min_less_than_max CHECK (min_amount <= max_amount)
);

CREATE TABLE member_grade_policy_history (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고객 등급 이력 ID (기본 키)',
                                             grade_id BIGINT NOT NULL COMMENT '원래 고객 등급 ID',
                                             grade_name VARCHAR(50) NOT NULL COMMENT '고객 등급 이름(이전 데이터)',
                                             min_amount INT NOT NULL COMMENT '최소 구매 금액(이전 데이터)',
                                             max_amount INT NOT NULL COMMENT '최대 구매 금액(이전 데이터)',
                                             period_days INT NOT NULL COMMENT '고객 등급 기간(이전 데이터)',
                                             description VARCHAR(255) NULL COMMENT '고객 등급 설명(이전 데이터)',
                                             created_date DATETIME(6) NULL COMMENT '고객 등급 원래 생성 날짜(이전)',
                                             last_modified_date DATETIME(6) NULL COMMENT '고객 등급 원래 수정 날짜(이전)',
                                             deleted_date DATETIME(6) NULL COMMENT '고객 등급 원래 삭제 날짜(이전)',
                                             operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                             history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                             CONSTRAINT FK_member_grade_policy_history_grade
                                                 FOREIGN KEY (grade_id) REFERENCES member_grade_policy (id) ON DELETE CASCADE
);


CREATE TABLE delivery_address (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '배송지 주소 ID (기본 키)',
                                  delivery_address VARCHAR(255) NOT NULL COMMENT '배송지 주소',
                                  delivery_address_line VARCHAR(255) NOT NULL COMMENT '배송지 상세주소',
                                  delivery_address_zip_code INT NOT NULL COMMENT '배송지 우편번호',
                                  address_type VARCHAR(255) NOT NULL COMMENT '주소 별명',
                                  is_primary_address BOOLEAN NOT NULL COMMENT '기본배송지 여부',
                                  is_latest_address BOOLEAN NOT NULL COMMENT '최근배송지 여부',
                                  created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                  last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                  deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE member (
                        id BIGINT NOT NULL PRIMARY KEY COMMENT '회원 ID (기본 키)',
                        name VARCHAR(255) NOT NULL COMMENT '회원 이름',
                        sex CHAR(1) NOT NULL COMMENT '성별',
                        birthdate DATE NOT NULL COMMENT '생년월일',
                        email VARCHAR(255) NOT NULL UNIQUE COMMENT '회원 이메일',
                        password VARCHAR(255) NOT NULL COMMENT '비밀번호',
                        address VARCHAR(255) NULL COMMENT '주소',
                        address_line VARCHAR(255) NULL COMMENT '상세주소',
                        address_zip_code VARCHAR(255) NULL COMMENT '우편번호',
                        delivery_address_id BIGINT NULL COMMENT '배송지 주소',
                        mobile VARCHAR(255) NULL COMMENT '휴대폰 번호',
                        landline VARCHAR(255) NULL COMMENT '전화번호',
                        grade_id BIGINT NULL COMMENT '등급 ID (외래 키)',
                        current_points INT NOT NULL DEFAULT 0 COMMENT '현재 보유 포인트',
                        total_earned_points INT NOT NULL DEFAULT 0 COMMENT '총 얻은 포인트',
                        total_redeemed_points INT NOT NULL DEFAULT 0 COMMENT '총 사용한 포인트',
                        member_status VARCHAR(255) NULL COMMENT '회원 상태',
                        latest_login_date DATETIME NULL COMMENT '마지막 로그인 날짜',
                        created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                        last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                        deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                        CONSTRAINT FK_member_grade_policy FOREIGN KEY (grade_id) REFERENCES member_grade_policy (id),
                        CONSTRAINT FK_delivery_address FOREIGN KEY (delivery_address_id) REFERENCES delivery_address (id)
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