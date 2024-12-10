-- =========================================
-- 4. 포인트 및 정책 관련 코드 테이블
-- =========================================

-- 포인트 거래 코드 테이블
CREATE TABLE point_transaction_code (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '포인트 거래 코드 ID (기본 키)',
                                        name VARCHAR(50) NOT NULL UNIQUE COMMENT '포인트 거래 코드 이름',
                                        description VARCHAR(255) NULL COMMENT '포인트 거래 코드 설명',
                                        created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                        last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                        deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 회원 포인트 테이블
CREATE TABLE member_point (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '멤버 포인트 ID (기본 키)',
                              member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                              current_points INT NOT NULL DEFAULT 0 COMMENT '현재 보유 포인트',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                              CONSTRAINT FK_member_point_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);

-- 포인트 이력 테이블
CREATE TABLE point_transaction (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '포인트 거래 ID (기본 키)',
                                   member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                                   amount INT NOT NULL COMMENT '포인트 증감량(양수: 적립, 음수: 사용)',
                                   point_transaction_code_id BIGINT NOT NULL COMMENT '포인트 거래 코드 ID (외래 키)',
                                   transaction_type VARCHAR(50) NOT NULL COMMENT '거래 유형 (EARN/SPEND)',
                                   description VARCHAR(255) NULL COMMENT '거래 설명',
                                   reference_order_id BIGINT NULL COMMENT '연관된 주문 ID',
                                   created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                   last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                   deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                   CHECK (amount <> 0),
                                   CONSTRAINT FK_point_transaction_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
                                   CONSTRAINT FK_point_transaction_code FOREIGN KEY (point_transaction_code_id) REFERENCES point_transaction_code (id),
                                   CONSTRAINT FK_point_transaction_order FOREIGN KEY (reference_order_id) REFERENCES member_order_main (id)
);

-- 퍼센테이지 기반 포인트 정책 테이블 (상위 정책 정의)
CREATE TABLE percentage_point_policy (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '퍼센트 기반 포인트 정책 ID (기본 키)',
                                         member_grade_policy_id BIGINT NOT NULL COMMENT '등급 ID (외래 키)',
                                         description VARCHAR(255) NULL COMMENT '정책 설명',
                                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                         deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                         CONSTRAINT FK_percentage_point_policy_grade FOREIGN KEY (member_grade_policy_id) REFERENCES member_grade_policy (id)
);

-- 퍼센테이지 포인트 액/비율 관리 테이블 (기간, 비율을 여기에 관리)
CREATE TABLE percentage_point_amount (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '퍼센트 포인트 비율 관리 ID (기본 키)',
                                         percentage_policy_id BIGINT NOT NULL COMMENT '퍼센트 기반 포인트 정책 ID (외래 키)',
                                         percentage DECIMAL(5,2) NOT NULL COMMENT '적립율(%)',
                                         effective_start_date DATETIME(6) NOT NULL COMMENT '정책 유효 시작 날짜',
                                         effective_end_date DATETIME(6) DEFAULT NULL COMMENT '정책 유효 종료 날짜',
                                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                         deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                         CONSTRAINT FK_percentage_point_amount_policy FOREIGN KEY (percentage_policy_id) REFERENCES percentage_point_policy (id)
);


-- 고정 포인트 정책 테이블 (상위 정책 정의)
CREATE TABLE fixed_point_policy (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고정 포인트 정책 ID (기본 키)',
                                    policy_name VARCHAR(50) NOT NULL UNIQUE COMMENT '정책 이름',
                                    description VARCHAR(255) NULL COMMENT '정책 설명',
                                    created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                    last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                    deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 고정 포인트 액 관리 테이블 (기간, 액수를 여기에 관리)
CREATE TABLE fixed_point_amount (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고정 포인트 액 관리 ID (기본 키)',
                                    fixed_policy_id BIGINT NOT NULL COMMENT '고정 포인트 정책 ID (외래 키)',
                                    point_amount INT NOT NULL COMMENT '지급 포인트 양',
                                    effective_start_date DATETIME(6) NOT NULL COMMENT '정책 유효 시작 날짜(이벤트 기간 시작)',
                                    effective_end_date DATETIME(6) DEFAULT NULL COMMENT '정책 유효 종료 날짜(이벤트 기간 종료)',
                                    created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                    last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                    deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                    CONSTRAINT FK_fixed_point_amount_policy FOREIGN KEY (fixed_policy_id) REFERENCES fixed_point_policy (id)
);

