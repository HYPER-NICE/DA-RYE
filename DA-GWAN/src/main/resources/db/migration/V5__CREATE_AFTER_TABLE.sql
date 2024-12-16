-- =========================================
-- 의존성 테이블 생성
-- 의존성이 있어, 엔티티 스텝에서 생성할 수 없는 테이블을 작성합니다.
-- =========================================

CREATE TABLE POINT_TRANSACTION_TYPE
(
    -- 기본키
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '공통 코드 고유 ID (기본 키)',

    -- 데이터 컬럼
    CODE_TYPE            VARCHAR(50)  NOT NULL COMMENT '코드 유형(예: POINT_TRANSACTION_TYPE, ORDER_STATUS 등)',
    CODE_VALUE           VARCHAR(50)  NOT NULL COMMENT '코드 값(예: SAVE, USE, CANCEL)',
    NAME                 VARCHAR(100) NOT NULL COMMENT '코드 명칭(예: 포인트 적립, 포인트 사용, 포인트 취소)',
    DESCRIPTION          VARCHAR(255) NULL COMMENT '코드에 대한 상세 설명',

    -- 유니크 제약조건: 동일한 CODE_TYPE 내에서 CODE_VALUE는 유니크하게 유지
    UNIQUE KEY (CODE_TYPE, CODE_VALUE),

    -- 시스템 관리 컬럼
    CREATED_DATE         DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '레코드 생성 날짜 및 시간',
    LAST_MODIFIED_DATE   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '마지막 수정 날짜 및 시간',
    DELETED_DATE         DATETIME(6) DEFAULT NULL COMMENT '레코드가 논리적 삭제된 날짜'
)
    COMMENT = '공통 코드 테이블로, 다양한 유형의 코드를 관리하기 위한 테이블입니다. POINT_TRANSACTION_TYPE, ORDER_STATUS 등 다양한 코드 유형을 저장할 수 있습니다.';


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
    CONSTRAINT FK_POINT_TRANSACTION_TYPE FOREIGN KEY (POINT_TRANSACTION_TYPE_ID) REFERENCES POINT_TRANSACTION_TYPE (ID),
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

