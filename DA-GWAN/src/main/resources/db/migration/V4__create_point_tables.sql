-- 포인트 거래 상태 테이블
CREATE TABLE point_transaction_status (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '포인트 거래 상태 ID (기본 키)',
                                          name VARCHAR(50) NOT NULL UNIQUE COMMENT '포인트 거래 상태 이름',
                                          description VARCHAR(255) NULL COMMENT '포인트 거래 상태 설명',
                                          created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                          last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                          deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 기본 포인트 거래 상태 데이터 삽입
INSERT INTO point_transaction_status (name, description)
VALUES
    ('PENDING', '포인트 적립/사용 대기'),
    ('COMPLETED', '포인트 적립/사용 완료'),
    ('CANCELLED', '포인트 적립/사용 취소');

-- 멤버 포인트 현황 테이블
CREATE TABLE member_point (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '멤버 포인트 ID (기본 키)',
                              member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                              current_points INT NOT NULL DEFAULT 0 COMMENT '현재 보유 포인트',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 포인트 거래(이력) 테이블
CREATE TABLE point_transaction (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '포인트 거래 ID (기본 키)',
                                   member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                                   amount INT NOT NULL COMMENT '포인트 증감량(양수: 적립, 음수: 사용)',
                                   status_id BIGINT NOT NULL COMMENT '포인트 거래 상태 ID (외래 키)',
                                   transaction_type VARCHAR(50) NOT NULL COMMENT '거래 유형 (EARN: 적립, SPEND: 사용)',
                                   description VARCHAR(255) NULL COMMENT '거래 설명',
                                   reference_order_id BIGINT NULL COMMENT '연관된 주문 ID (필요 시)',
                                   created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                   last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                   deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                   CHECK (amount <> 0)
);

-- 퍼센트 기반 포인트 정책 테이블(등급별 적립율)
CREATE TABLE percentage_point_policy (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '퍼센트 기반 포인트 정책 ID (기본 키)',
                                         member_grade_id BIGINT NOT NULL COMMENT '등급 ID (외래 키)',
                                         percentage DECIMAL(5,2) NOT NULL COMMENT '적립율(%) 예: 1.00, 2.00, 3.00 등',
                                         effective_start_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '정책 유효 시작 날짜',
                                         effective_end_date DATETIME(6) DEFAULT NULL COMMENT '정책 유효 종료 날짜(없으면 계속 유효)',
                                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                         deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 예시 정책 데이터 삽입
-- 등급에 따른 적립율 예: 마루한(하위 등급) 1%, 다소니 2%, 벗드리 3%, 나누리 4%
INSERT INTO percentage_point_policy (member_grade_id, percentage)
SELECT id, CASE grade_name
               WHEN '마루한' THEN 1.00
               WHEN '다소니' THEN 2.00
               WHEN '벗드리' THEN 3.00
               WHEN '나누리' THEN 4.00
    END
FROM member_grade;

-- 고정 포인트 정책 테이블(친구 초대, 신규 가입, 생일 포인트 등)
CREATE TABLE fixed_point_policy (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고정 포인트 정책 ID (기본 키)',
                                    policy_name VARCHAR(50) NOT NULL UNIQUE COMMENT '정책 이름 (예: FRIEND_INVITE, NEW_SIGNUP, BIRTHDAY)',
                                    description VARCHAR(255) NULL COMMENT '정책 설명',
                                    point_amount INT NOT NULL COMMENT '지급 포인트 양',
                                    effective_start_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '정책 유효 시작 날짜',
                                    effective_end_date DATETIME(6) DEFAULT NULL COMMENT '정책 유효 종료 날짜(없으면 계속 유효)',
                                    created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                    last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                    deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 예시 고정 포인트 정책 데이터 삽입
INSERT INTO fixed_point_policy (policy_name, description, point_amount)
VALUES
    ('FRIEND_INVITE', '친구 초대 포인트 지급', 500),
    ('NEW_SIGNUP', '신규 회원 가입 포인트 지급', 1000),
    ('BIRTHDAY', '생일 포인트 지급', 2000);

