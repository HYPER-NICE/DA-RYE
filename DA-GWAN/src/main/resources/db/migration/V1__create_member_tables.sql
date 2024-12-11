CREATE TABLE MEMBER_GRADE_POLICY (
                                     ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고객 등급 ID (기본 키)',
                                     GRADE_NAME VARCHAR(50) NOT NULL COMMENT '고객 등급 이름',
                                     MIN_AMOUNT INT NOT NULL COMMENT '최소 구매 금액',
                                     MAX_AMOUNT INT NOT NULL COMMENT '최대 구매 금액',
                                     PERIOD_DAYS INT NOT NULL COMMENT '등급 기준 기간(일)',
                                     DESCRIPTION VARCHAR(255) NULL COMMENT '고객 등급 설명',
                                     CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     DELETED_DATE DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                     CONSTRAINT CHK_MIN_LESS_THAN_MAX CHECK (MIN_AMOUNT <= MAX_AMOUNT)
);

CREATE TABLE MEMBER_GRADE_POLICY_HISTORY (
                                             ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고객 등급 이력 ID (기본 키)',
                                             GRADE_ID BIGINT NOT NULL COMMENT '원래 고객 등급 ID',
                                             GRADE_NAME VARCHAR(50) NOT NULL COMMENT '고객 등급 이름(이전 데이터)',
                                             MIN_AMOUNT INT NOT NULL COMMENT '최소 구매 금액(이전 데이터)',
                                             MAX_AMOUNT INT NOT NULL COMMENT '최대 구매 금액(이전 데이터)',
                                             PERIOD_DAYS INT NOT NULL COMMENT '고객 등급 기간(이전 데이터)',
                                             DESCRIPTION VARCHAR(255) NULL COMMENT '고객 등급 설명(이전 데이터)',
                                             CREATED_DATE DATETIME(6) NULL COMMENT '고객 등급 원래 생성 날짜(이전)',
                                             LAST_MODIFIED_DATE DATETIME(6) NULL COMMENT '고객 등급 원래 수정 날짜(이전)',
                                             DELETED_DATE DATETIME(6) NULL COMMENT '고객 등급 원래 삭제 날짜(이전)',
                                             OPERATION_TYPE VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                             HISTORY_CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                             CONSTRAINT FK_MEMBER_GRADE_POLICY_HISTORY_GRADE
                                                 FOREIGN KEY (GRADE_ID) REFERENCES MEMBER_GRADE_POLICY (ID) ON DELETE CASCADE
);


CREATE TABLE DELIVERY_ADDRESS (
                                  ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '배송지 주소 ID (기본 키)',
                                  DELIVERY_ADDRESS VARCHAR(255) NOT NULL COMMENT '배송지 주소',
                                  DELIVERY_ADDRESS_LINE VARCHAR(255) NOT NULL COMMENT '배송지 상세주소',
                                  DELIVERY_ADDRESS_ZIP_CODE INT NOT NULL COMMENT '배송지 우편번호',
                                  ADDRESS_TYPE VARCHAR(255) NOT NULL COMMENT '주소 별명',
                                  IS_PRIMARY_ADDRESS BOOLEAN NOT NULL COMMENT '기본배송지 여부',
                                  IS_LATEST_ADDRESS BOOLEAN NOT NULL COMMENT '최근배송지 여부',
                                  CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                  LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                  DELETED_DATE DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE MEMBER (
                        ID BIGINT NOT NULL PRIMARY KEY COMMENT '회원 ID (기본 키)',
                        NAME VARCHAR(255) NOT NULL COMMENT '회원 이름',
                        SEX CHAR(1) NOT NULL COMMENT '성별',
                        BIRTHDATE DATE NOT NULL COMMENT '생년월일',
                        EMAIL VARCHAR(255) NOT NULL UNIQUE COMMENT '회원 이메일',
                        PASSWORD VARCHAR(255) NOT NULL COMMENT '비밀번호',
                        ADDRESS VARCHAR(255) NULL COMMENT '주소',
                        ADDRESS_LINE VARCHAR(255) NULL COMMENT '상세주소',
                        ADDRESS_ZIP_CODE VARCHAR(255) NULL COMMENT '우편번호',
                        DELIVERY_ADDRESS_ID BIGINT NULL COMMENT '배송지 주소',
                        MOBILE VARCHAR(255) NULL COMMENT '휴대폰 번호',
                        LANDLINE VARCHAR(255) NULL COMMENT '전화번호',
                        GRADE_ID BIGINT NULL COMMENT '등급 ID (외래 키)',
                        CURRENT_POINTS INT NOT NULL DEFAULT 0 COMMENT '현재 보유 포인트',
                        TOTAL_EARNED_POINTS INT NOT NULL DEFAULT 0 COMMENT '총 얻은 포인트',
                        TOTAL_REDEEMED_POINTS INT NOT NULL DEFAULT 0 COMMENT '총 사용한 포인트',
                        MEMBER_STATUS VARCHAR(255) NULL COMMENT '회원 상태',
                        LATEST_LOGIN_DATE DATETIME NULL COMMENT '마지막 로그인 날짜',
                        CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                        LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                        DELETED_DATE DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                        CONSTRAINT FK_MEMBER_GRADE_POLICY FOREIGN KEY (GRADE_ID) REFERENCES MEMBER_GRADE_POLICY (ID),
                        CONSTRAINT FK_DELIVERY_ADDRESS FOREIGN KEY (DELIVERY_ADDRESS_ID) REFERENCES DELIVERY_ADDRESS (ID)
);

CREATE TABLE MEMBER_HISTORY (
                                ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '멤버 이력 ID (기본 키)',
                                MEMBER_ID BIGINT NOT NULL COMMENT '이력 대상 회원 ID',
                                NAME VARCHAR(255) NOT NULL COMMENT '회원 이름(이전 데이터)',
                                EMAIL VARCHAR(255) NOT NULL COMMENT '회원 이메일(이전 데이터)',
                                PASSWORD VARCHAR(255) NOT NULL COMMENT '비밀번호(이전 데이터)',
                                ADDRESS VARCHAR(255) NULL COMMENT '주소(이전 데이터)',
                                MOBILE VARCHAR(255) NULL COMMENT '휴대폰(이전 데이터)',
                                GRADE_ID BIGINT NULL COMMENT '등급 ID(이전 데이터)',
                                CREATED_DATE DATETIME(6) NULL COMMENT '회원 원래 생성 날짜(이전)',
                                LAST_MODIFIED_DATE DATETIME(6) NULL COMMENT '회원 원래 수정 날짜(이전)',
                                DELETED_DATE DATETIME(6) NULL COMMENT '회원 원래 삭제 날짜(이전)',
                                OPERATION_TYPE VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                HISTORY_CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                CONSTRAINT FK_MEMBER_HISTORY_MEMBER
                                    FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID) ON DELETE CASCADE
);


CREATE TABLE CATEGORY (
                          ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID (기본 키)',
                          NAME VARCHAR(255) NOT NULL UNIQUE COMMENT '카테고리 이름',
                          PARENT_ID BIGINT NULL COMMENT '부모 카테고리 ID',
                          SCREEN_ORDER BIGINT NOT NULL COMMENT '화면에 보여질 정렬 순서',
                          CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                          LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                          DELETED_DATE DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                          CONSTRAINT FK_CATEGORY_PARENT FOREIGN KEY (PARENT_ID) REFERENCES CATEGORY (ID)
);

CREATE TABLE CATEGORY_HISTORY (
                                  ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 이력 ID (기본 키)',
                                  CATEGORY_ID BIGINT NOT NULL COMMENT '원래 카테고리 ID',
                                  NAME VARCHAR(255) NOT NULL COMMENT '카테고리 이름(이전)',
                                  PARENT_ID BIGINT NULL COMMENT '부모 카테고리 ID(이전)',
                                  CREATED_DATE DATETIME(6) NULL COMMENT '카테고리 원래 생성 날짜(이전)',
                                  LAST_MODIFIED_DATE DATETIME(6) NULL COMMENT '카테고리 원래 수정 날짜(이전)',
                                  DELETED_DATE DATETIME(6) NULL COMMENT '카테고리 원래 삭제 날짜(이전)',
                                  OPERATION_TYPE VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                  HISTORY_CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                  CONSTRAINT FK_CATEGORY_HISTORY_CATEGORY
                                      FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (ID) ON DELETE CASCADE
);

CREATE TABLE PRODUCT_STATUS_CODE (
                                     ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '상품 상태 코드 ID (기본 키)',
                                     NAME VARCHAR(255) NOT NULL UNIQUE COMMENT '상품 상태 코드 이름',
                                     DESCRIPTION VARCHAR(255) NULL COMMENT '상품 상태 코드 설명',
                                     CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     DELETED_DATE DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

CREATE TABLE PRODUCT (
                         ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 ID (기본 키)',
                         NAME VARCHAR(255) NOT NULL COMMENT '제품 이름',
                         SHORT_DESCRIPTION TEXT NULL COMMENT '제품 간단 설명',
                         LONG_DESCRIPTION TEXT NULL COMMENT '제품 상세 설명',
                         MAIN_DESCRIPTION VARCHAR(1000) NULL COMMENT '제품 본문 내용',
                         PRICE INT DEFAULT 0 NOT NULL COMMENT '판매 가격',
                         CATEGORY_ID BIGINT NOT NULL COMMENT '카테고리 ID (외래 키)',
                         STATUS_ID BIGINT NOT NULL COMMENT '상품 상태 ID (외래 키)',
                         IMPORTER VARCHAR(255) NULL COMMENT '수입원',
                         MANUFACTURER VARCHAR(255) NULL COMMENT '제조원',
                         EXPIRATION_DATE DATETIME(6) NOT NULL COMMENT '소비기한',
                         SIZE VARCHAR(255) NULL COMMENT '사이즈',
                         CAPACITY VARCHAR(255) NULL COMMENT '용량(수량)',
                         INGREDIENTS VARCHAR(255) NULL COMMENT '원재료명 및 성분',
                         PRECAUTIONS VARCHAR(255) NULL COMMENT '주의사항',
                         SALE_DATE DATETIME(6) NOT NULL COMMENT '판매시작일',
                         NEW BOOLEAN DEFAULT FALSE COMMENT '신상품',
                         HOT BOOLEAN DEFAULT FALSE COMMENT '최근 인기 상품',
                         BEST BOOLEAN DEFAULT FALSE COMMENT '인기 상품',
                         CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                         LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                         DELETED_DATE DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                         CHECK (PRICE >= 0),
                         CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (ID),
                         CONSTRAINT FK_PRODUCT_STATUS_CODE FOREIGN KEY (STATUS_ID) REFERENCES PRODUCT_STATUS_CODE (ID)
);

CREATE TABLE PRODUCT_HISTORY (
                                 ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 이력 ID (기본 키)',
                                 PRODUCT_ID BIGINT NOT NULL COMMENT '원래 제품 ID',
                                 NAME VARCHAR(255) NOT NULL COMMENT '제품 이름(이전)',
                                 DESCRIPTION TEXT NULL COMMENT '제품 설명(이전)',
                                 PRICE INT NOT NULL COMMENT '판매 가격(이전)',
                                 CATEGORY_ID BIGINT NOT NULL COMMENT '카테고리 ID(이전)',
                                 CREATED_DATE DATETIME(6) NULL COMMENT '제품 원래 생성 날짜(이전)',
                                 LAST_MODIFIED_DATE DATETIME(6) NULL COMMENT '제품 원래 수정 날짜(이전)',
                                 DELETED_DATE DATETIME(6) NULL COMMENT '제품 원래 삭제 날짜(이전)',
                                 OPERATION_TYPE VARCHAR(10) NOT NULL COMMENT '조작 유형(UPDATE/DELETE)',
                                 HISTORY_CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                 CONSTRAINT FK_PRODUCT_HISTORY_PRODUCT
                                     FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID) ON DELETE CASCADE
);

CREATE TABLE PRICE_HISTORY (
                                     ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '가격 이력 ID (기본 키)',
                                     PRODUCT_ID BIGINT NOT NULL COMMENT '원래 상품 ID(외래 키)',
                                     PRICE INT NOT NULL COMMENT '판매 가격',
                                     CHANGE_START_DATE DATETIME(6) NULL COMMENT '변경 시작 일자',
                                     CHANGE_END_DATE DATETIME(6) NULL COMMENT '변경 종료 일자',
                                     CREATED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     LAST_MODIFIED_DATE DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     DELETED_DATE DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                     CONSTRAINT FK_PRODUCT_ID FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID)
);