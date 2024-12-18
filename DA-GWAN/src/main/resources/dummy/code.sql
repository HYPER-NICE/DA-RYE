-- ========================================================
-- PRODUCT_STATUS
-- ========================================================

INSERT INTO PRODUCT_STATUS (
    NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE
) VALUES
      ('판매 대기', '판매 예정중이라 대기가 걸려있는 상태', NOW(), NOW(), NULL, NULL),
      ('판매 중', '현재 판매 중인 상태', NOW(), NOW(), NULL, NULL),
      ('재고없음', '입고된 상품이 다 팔려 재고가 떨어진 상태', NOW(), NOW(), NULL, NULL),
      ('판매 중지', '여러 이유로 판매가 중지된 상태', NOW(), NOW(), NULL, NULL);


-- ========================================================
-- PRODUCT_STATUS
-- ========================================================
-- 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 테이블 비우기
TRUNCATE TABLE POINT_TRANSACTION_TYPE;

-- 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;
-- ========================================================
-- PRODUCT_STATUS
-- ========================================================

INSERT INTO POINT_TRANSACTION_TYPE (
    CODE_TYPE, CODE_VALUE, NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, DELETED_DATE
) VALUES
-- 포인트 거래 유형: 적립
('POINT_TRANSACTION_TYPE', 'save', '포인트 적립', '고객이 적립한 포인트 거래 코드', NOW(), NOW(), NULL),

-- 포인트 거래 유형: 사용
('POINT_TRANSACTION_TYPE', 'use', '포인트 사용', '고객이 사용한 포인트 거래 코드', NOW(), NOW(), NULL),

-- 포인트 거래 유형: 소멸
('POINT_TRANSACTION_TYPE', 'cancel', '포인트 소멸', '미로그인 1년 경과로 소멸된 포인트 거래 코드', NOW(), NOW(), NULL);
