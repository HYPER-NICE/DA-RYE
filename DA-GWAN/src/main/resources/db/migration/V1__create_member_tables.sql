-- 멤버 등급 테이블 생성
CREATE TABLE member_grade (
                              id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '등급 ID (기본 키)',
                              grade_name VARCHAR(50) NOT NULL COMMENT '등급 이름',
                              min_amount INT NOT NULL COMMENT '최소 구매 금액',
                              max_amount INT NOT NULL COMMENT '최대 구매 금액',
                              period_days INT NOT NULL COMMENT '등급 기준 기간 (일)',
                              description VARCHAR(255) NULL COMMENT '등급 설명',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                              CONSTRAINT chk_min_less_than_max CHECK (min_amount <= max_amount)
);

-- 멤버 등급 히스토리 테이블 생성
CREATE TABLE member_grade_history (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '등급 이력 ID (기본 키)',
                                      grade_id BIGINT NOT NULL COMMENT '원래 등급 ID',
                                      grade_name VARCHAR(50) NOT NULL COMMENT '등급 이름(이전 데이터)',
                                      min_amount INT NOT NULL COMMENT '최소 구매 금액(이전 데이터)',
                                      max_amount INT NOT NULL COMMENT '최대 구매 금액(이전 데이터)',
                                      period_days INT NOT NULL COMMENT '등급 기준 기간 (일)(이전 데이터)',
                                      description VARCHAR(255) NULL COMMENT '등급 설명(이전 데이터)',
                                      created_date DATETIME(6) NULL COMMENT '등급 레코드의 원래 생성 날짜(이전 데이터)',
                                      last_modified_date DATETIME(6) NULL COMMENT '등급 레코드의 원래 수정 날짜(이전 데이터)',
                                      deleted_date DATETIME(6) NULL COMMENT '등급 레코드의 원래 삭제 날짜(이전 데이터)',
                                      operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형 (UPDATE 또는 DELETE)',
                                      history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'
);


-- 멤버 테이블 생성
CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT '회원 ID (기본 키)',
                        name VARCHAR(255) NOT NULL COMMENT '회원 이름',
                        email VARCHAR(255) NOT NULL UNIQUE COMMENT '회원 이메일',
                        password VARCHAR(255) NOT NULL COMMENT '비밀번호',
                        address VARCHAR(255) NULL COMMENT '주소',
                        mobile VARCHAR(255) NULL COMMENT '휴대폰 번호',
                        grade_id BIGINT NULL COMMENT '등급 ID (외래 키)',
                        created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                        last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                        deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 멤버 업데이트 이력 테이블
CREATE TABLE member_history (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '멤버 이력 ID (기본 키)',
                                member_id BIGINT NOT NULL COMMENT '히스토리가 기록된 회원 ID',
                                name VARCHAR(255) NOT NULL COMMENT '회원 이름(이전 데이터)',
                                email VARCHAR(255) NOT NULL COMMENT '회원 이메일(이전 데이터)',
                                password VARCHAR(255) NOT NULL COMMENT '비밀번호(이전 데이터)',
                                address VARCHAR(255) NULL COMMENT '주소(이전 데이터)',
                                mobile VARCHAR(255) NULL COMMENT '휴대폰 번호(이전 데이터)',
                                grade_id BIGINT NULL COMMENT '등급 ID(이전 데이터)',
                                created_date DATETIME(6) NULL COMMENT '회원 레코드의 원래 생성 날짜(이전 데이터)',
                                last_modified_date DATETIME(6) NULL COMMENT '회원 레코드의 원래 수정 날짜(이전 데이터)',
                                deleted_date DATETIME(6) NULL COMMENT '회원 레코드의 원래 삭제 날짜(이전 데이터)',
                                operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형 (UPDATE 또는 DELETE)',
                                history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜'
);

-- =======================
-- 정식 데이터 삽입
-- =======================
INSERT INTO member_grade (grade_name, min_amount, max_amount, period_days, description)
VALUES
    ('나누리', 100000, 999999999, 360, '최근 360일 동안 10만원 이상 구매 고객'),
    ('벗드리', 50000, 99999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객'),
    ('다소니', 10000, 49999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객'),
    ('마루한', 0, 9999, 90, '최근 90일 동안 1만원 미만 구매 고객');
