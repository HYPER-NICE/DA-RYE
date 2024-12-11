-- =========================================
-- 5. 포인트 및 정책 관련 코드 테이블
-- =========================================

-- 약관 메인 테이블 (약관 종류 정의)
CREATE TABLE terms (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '약관 ID (기본 키)',
                       name VARCHAR(100) NOT NULL UNIQUE COMMENT '약관 이름 (예: 이용약관, 개인정보처리방침 등)',
                       description VARCHAR(255) NULL COMMENT '약관 설명',
                       required BOOLEAN NOT NULL DEFAULT TRUE COMMENT '필수 동의 여부',
                       activated BOOLEAN NOT NULL DEFAULT TRUE COMMENT '약관 활성화 여부',
                       created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                       last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                       deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 약관 버전 관리 테이블 (메인 약관의 버전별 내용)
CREATE TABLE terms_version (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '약관 버전 ID (기본 키)',
                               terms_id BIGINT NOT NULL COMMENT '약관 ID (외래 키)',
                               version_no INT NOT NULL COMMENT '약관 버전 번호',
                               content TEXT NOT NULL COMMENT '약관 내용',
                               effective_start_date DATETIME(6) NOT NULL COMMENT '약관 유효 시작 날짜',
                               effective_end_date DATETIME(6) DEFAULT NULL COMMENT '약관 유효 종료 날짜',
                               created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                               last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                               deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                               CONSTRAINT FK_terms_version_terms FOREIGN KEY (terms_id) REFERENCES terms (id)
);

-- 서브 약관 테이블 (메인 약관 하위에 속하는 세부 약관 정의)
CREATE TABLE sub_terms (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '서브 약관 ID (기본 키)',
                           terms_id BIGINT NOT NULL COMMENT '메인 약관 ID (외래 키)',
                           name VARCHAR(100) NOT NULL COMMENT '서브 약관 이름 (예: 개인정보 이용동의, 위치정보 이용동의 등)',
                           description VARCHAR(255) NULL COMMENT '서브 약관 설명',
                           required BOOLEAN NOT NULL DEFAULT TRUE COMMENT '필수 동의 여부',
                           activated BOOLEAN NOT NULL DEFAULT TRUE COMMENT '약관 활성화 여부',
                           created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                           last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                           deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                           CONSTRAINT FK_sub_terms_terms FOREIGN KEY (terms_id) REFERENCES terms (id)
);

-- 서브 약관 버전 관리 테이블 (서브 약관의 버전별 내용)
CREATE TABLE sub_terms_version (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '서브 약관 버전 ID (기본 키)',
                                   sub_terms_id BIGINT NOT NULL COMMENT '서브 약관 ID (외래 키)',
                                   version_no INT NOT NULL COMMENT '서브 약관 버전 번호',
                                   content TEXT NOT NULL COMMENT '서브 약관 내용',
                                   effective_start_date DATETIME(6) NOT NULL COMMENT '서브 약관 유효 시작 날짜',
                                   effective_end_date DATETIME(6) DEFAULT NULL COMMENT '서브 약관 유효 종료 날짜',
                                   created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                   last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                   deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                   CONSTRAINT FK_sub_terms_version_sub_terms FOREIGN KEY (sub_terms_id) REFERENCES sub_terms (id)
);

-- 회원이 어떤 약관/서브약관을 어느 버전에 동의했는지 기록하는 테이블
CREATE TABLE member_terms_agreement (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '멤버 약관 동의 ID (기본 키)',
                                        member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                                        agreed_terms_version_id BIGINT NULL COMMENT '약관 버전 ID (외래 키) - 메인 약관 동의 시',
                                        agreed_sub_terms_version_id BIGINT NULL COMMENT '서브 약관 버전 ID (외래 키) - 서브 약관 동의 시',
                                        agreed_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '동의한 날짜',
                                        last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '동의 변경 날짜',
                                        deleted_date DATETIME(6) DEFAULT NULL COMMENT '동의 취소 날짜',
                                        CONSTRAINT FK_member_terms_agreement_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
                                        CONSTRAINT FK_member_terms_agreement_terms_version FOREIGN KEY (agreed_terms_version_id) REFERENCES terms_version (id),
                                        CONSTRAINT FK_member_terms_agreement_sub_terms_version FOREIGN KEY (agreed_sub_terms_version_id) REFERENCES sub_terms_version (id)
);
