-- =========================================
-- 의존성 테이블 생성
-- 의존성이 있어, 엔티티 스텝에서 생성할 수 없는 테이블을 작성합니다.
-- =========================================

-- 포인트 이력 테이블
CREATE TABLE POINT_HISTORY
(
    -- 기본키
    ID                        BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '포인트 발생 ID',

    -- 외래키
    MEMBER_ID                 BIGINT       NOT NULL COMMENT '회원 ID (외래 키)',
    POINT_TRANSACTION_TYPE_ID BIGINT       NOT NULL COMMENT '포인트 거래 유형 ID (외래 키)',
    ORDER_MAIN_ID             BIGINT       NULL COMMENT '주문 ID',
    CONSTRAINT FK_POINT_TRANSACTION_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID) ON DELETE CASCADE,
    CONSTRAINT FK_POINT_TRANSACTION_TYPE FOREIGN KEY (POINT_TRANSACTION_TYPE_ID) REFERENCES COMMON_CODE (ID),
    CONSTRAINT FK_POINT_TRANSACTION_ORDER FOREIGN KEY (ORDER_MAIN_ID) REFERENCES ORDER_MAIN (ID),

    -- 데이터
    AMOUNT                    INT          NOT NULL COMMENT '포인트 증감량(양수: 적립, 음수: 차감)',
    DESCRIPTION               VARCHAR(255) NULL COMMENT '거래 설명',
    CHECK (AMOUNT <> 0),

    -- 시스템 컬럼
    CREATED_DATE              DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
    LAST_MODIFIED_DATE        DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
    DELETED_DATE              DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

