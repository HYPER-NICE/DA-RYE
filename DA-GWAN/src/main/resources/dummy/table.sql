-- 프로젝트 보여주기 용도의 더미 데이터 입니다.


-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE MEMBER;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- V2 MEMBER
-- ==================================================
INSERT INTO MEMBER (
    EMAIL, ROLE, PASSWORD, NAME, SEX, BIRTHDATE, MOBILE, POINT, LOCKED, PW_FAILED_COUNT,
    LATEST_LOGIN_DATE, REG_DATE, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
-- 첫 번째 사용자
('john.doe@example.com', 'USER','p123', 'John Doe', 'M', '1990-01-01', '010-1234-5678', 100, FALSE, 0,
 NULL, NOW(), NOW(), NOW(), NULL, NULL),

-- 두 번째 사용자
('jane.smith@example.com', 'USER', 'p456', 'Jane Smith', 'F', '1995-05-15', '010-2345-6789', 200, FALSE, 0,
 NULL, NOW(), NOW(), NOW(), NULL, NULL),

-- 세 번째 사용자
('alex.kim@example.com', 'USER', 'p789', 'Alex Kim', 'O', '1988-10-10', '010-3456-7890', 50, TRUE, 2,
 '2024-06-01 08:00:00', NOW(), NOW(), NOW(), NULL, NULL),

-- 네 번째 사용자
('lisa.park@example.com', 'USER', 'p000', 'Lisa Park', 'F', '2000-12-25', '010-4567-8901', 300, FALSE, 1,
 '2024-06-02 10:00:00', NOW(), NOW(), NOW(), NULL, NULL),

-- 다섯 번째 사용자
('root@darye.dev', 'ADMIN','1234', 'root', 'M', '1992-07-20', '010-5678-9012', 0, FALSE, 3,
 '2024-06-02 15:00:00', NOW(), NOW(), NOW(), NULL, NULL);


-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE CATEGORY;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;
-- ==================================================
-- V3 Product - CATEGORY
-- ==================================================
-- 최상위 카테고리 (1단계)
INSERT INTO CATEGORY (PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (NULL, 1, '티 제품', '오설록의 다양한 차 제품을 소개합니다.', NOW(), NOW(), NULL, NULL),
    (NULL, 2, '티웨어', '차를 즐기기 위한 다양한 티웨어.', NOW(), NOW(), NULL, NULL),
    (NULL, 3, '선물세트', '소중한 사람에게 전하는 선물 세트.', NOW(), NOW(), NULL, NULL);

-- 티 제품 하위 카테고리 (2단계)
INSERT INTO CATEGORY (PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (1, 1, '녹차', '신선하고 깔끔한 녹차 제품.', NOW(), NOW(), NULL, NULL),
    (1, 2, '발효차', '깊고 진한 풍미의 발효차 제품.', NOW(), NOW(), NULL, NULL),
    (1, 3, '블렌디드 티', '다양한 재료로 블렌딩된 차 제품.', NOW(), NOW(), NULL, NULL);

-- 녹차 하위 카테고리 (3단계)
INSERT INTO CATEGORY (PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (4, 1, '잎차', '전통적인 방식으로 우려내는 녹차 잎차.', NOW(), NOW(), NULL, NULL),
    (4, 2, '티백', '간편하게 즐길 수 있는 녹차 티백.', NOW(), NOW(), NULL, NULL),
    (4, 3, '파우더', '녹차 가루로 다양한 음료에 활용.', NOW(), NOW(), NULL, NULL);

-- 블렌디드 티 하위 카테고리 (3단계)
INSERT INTO CATEGORY (PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (6, 1, '잎차', '다양한 재료를 블렌딩한 잎차.', NOW(), NOW(), NULL, NULL),
    (6, 2, '티백', '간편하게 즐기는 블렌디드 티 티백.', NOW(), NOW(), NULL, NULL),
    (6, 3, '파우더', '블렌디드 티 파우더, 달빛걷기 등 특별한 맛.', NOW(), NOW(), NULL, NULL);

-- 티웨어 하위 카테고리 (2단계)
INSERT INTO CATEGORY (PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (2, 1, '찻잔', '다양한 디자인의 찻잔.', NOW(), NOW(), NULL, NULL),
    (2, 2, '티포트', '차를 우리는 티포트 제품.', NOW(), NOW(), NULL, NULL),
    (2, 3, '차도구', '차를 준비하는 데 필요한 다양한 도구.', NOW(), NOW(), NULL, NULL);

-- 선물세트 하위 카테고리 (2단계)
INSERT INTO CATEGORY (PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (3, 1, '베스트셀러 세트', '가장 인기 있는 제품을 묶은 세트.', NOW(), NOW(), NULL, NULL),
    (3, 2, '프리미엄 세트', '고급 제품으로 구성된 특별한 선물 세트.', NOW(), NOW(), NULL, NULL);


select * from CATEGORY;
-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE PRODUCT;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;
-- ========================================================
-- V3 Product - Product
-- ========================================================

-- [PRE] PRODUCT_STATUS 테이블 seed data
-- PRODUCT 테이블 더미 데이터 삽입
INSERT INTO PRODUCT (
    CATEGORY_ID, PRODUCT_STATUS_CODE_ID, NAME, THUMBNAIL_IMAGE, SHORT_DESCRIPTION, DESCRIPTION_IMAGE,
    LONG_DESCRIPTION, PRICE, MANUFACTURER, EXPIRATION_DATE, INGREDIENTS, PRECAUTIONS, IMPORTER,
    SALE_DATE, CAPACITY, UNIT, QUANTITY, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE
) VALUES
-- 녹차 상품들
(1, 2, '세작', NULL, '신선하고 향긋한 어린 잎으로 만든 녹차', NULL,
 '녹차 잎의 부드러운 향과 깔끔한 맛을 즐길 수 있습니다.', 20000, '오설록', '2025-12-31 23:59:59', '녹차 100%', '고온에서 장시간 보관 금지', '오설록',
 '2024-06-01 09:00:00', 50, 'g', 100, NOW(), NOW(), 1, NULL),

(1, 2, '우전', NULL, '첫물차로 만든 고급 녹차', NULL,
 '봄의 첫 잎을 따서 만든 귀한 차입니다. 깊은 녹차의 풍미가 특징입니다.', 35000, '오설록', '2025-12-31 23:59:59', '녹차 100%', '습기 및 직사광선 피하기', '오설록',
 '2024-06-01 09:00:00', 40, 'g', 50, NOW(), NOW(), 1, NULL),

(1, 1, '제주 순수 녹차', NULL, '제주의 자연을 담은 프리미엄 녹차', NULL,
 '신선한 제주 녹차 잎으로 만든 고급 녹차입니다.', 22000, '오설록', '2025-12-31 23:59:59', '녹차 100%', '습기와 직사광선을 피하세요.', '오설록',
 '2024-06-01 09:00:00', 60, 'g', 90, NOW(), NOW(), 2, NULL),

-- 발효차 상품들
(2, 2, '청우롱', NULL, '깊고 진한 발효차의 풍미', NULL,
 '제주의 자연에서 발효된 차로 부드럽고 깊은 맛이 특징입니다.', 30000, '오설록', '2025-12-31 23:59:59', '발효차 100%', '고온 다습한 환경 피하기', '오설록',
 '2024-06-01 09:00:00', 100, 'g', 80, NOW(), NOW(), 2, NULL),

(2, 2, '제주 화산암차', NULL, '발효 과정을 거쳐 깊은 맛을 낸 발효차', NULL,
 '시간을 두고 발효시킨 부드럽고 진한 맛의 발효차입니다.', 32000, '오설록', '2025-12-31 23:59:59', '발효차 100%', '직사광선에 노출하지 마세요.', '오설록',
 '2024-06-01 09:00:00', 80, 'g', 70, NOW(), NOW(), 3, NULL),

-- 블렌디드 티 상품들
(3, 4, '귤꽃향 블라썸 티', NULL, '싱그러운 귤꽃향 가득한 블렌디드 티', NULL,
 '우아한 귤꽃향과 상큼한 제주 영귤이 조화롭게 블렌딩된 차입니다.', 25000, '오설록', '2025-12-31 23:59:59', '영귤 90%, 귤피 10%', '장시간 보관 시 향이 약해질 수 있습니다.', '오설록',
 '2024-06-01 09:00:00', 30, 'g', 70, NOW(), NOW(), 3, NULL),

(3, 2, '허니 레몬 티', NULL, '달콤한 꿀과 상큼한 레몬의 조화', NULL,
 '레몬과 꿀이 어우러져 상큼하고 달콤한 맛을 선사합니다.', 18000, '오설록', '2025-12-31 23:59:59', '레몬농축액, 꿀', '직사광선 피하고 서늘한 곳 보관', '오설록',
 '2024-06-01 09:00:00', 100, 'ml', 120, NOW(), NOW(), 3, NULL),

-- 티웨어 상품들
(4, 3, '유리 찻잔 세트', NULL, '고급스러운 유리 찻잔과 받침 세트', NULL,
 '티타임을 더욱 특별하게 만들어 줄 유리 찻잔 세트입니다.', 40000, '오설록', '2026-12-31 23:59:59', '유리 100%', '충격 주의, 깨질 수 있음', '오설록',
 '2024-06-01 09:00:00', 2, '개', 30, NOW(), NOW(), 2, NULL),

(4, 2, '프리미엄 티포트 세트', NULL, '티타임의 품격을 높여주는 프리미엄 티포트 세트', NULL,
 '유리와 스테인리스 소재로 제작된 고급스러운 티포트 세트입니다.', 60000, '오설록', '2026-12-31 23:59:59', '유리, 스테인리스', '충격에 주의하세요.', '오설록',
 '2024-06-01 09:00:00', 1, '세트', 20, NOW(), NOW(), 1, NULL),

-- 선물 세트
(5, 2, '티 베스트 셀렉션', NULL, '오설록의 인기 티를 모은 선물 세트', NULL,
 '다양한 차를 경험할 수 있는 베스트 셀렉션 세트입니다.', 55000, '오설록', '2025-12-31 23:59:59', '녹차, 발효차, 블렌디드 티', '습기 및 직사광선 피하기', '오설록',
 '2024-06-01 09:00:00', 120, 'g', 50, NOW(), NOW(), 1, NULL);


-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================

-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE STOCK;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;
-- ========================================================
-- V3 Product - STOCK (product 먼저 넣어야함)
-- ========================================================
-- 입고(+) 상황: 재고 추가
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (1, 50, '2024-06-01 10:00:00', 50, NOW(), NOW(), NULL, NULL), -- 제품 1: 초기 입고
    (2, 100, '2024-06-01 12:00:00', 100, NOW(), NOW(), NULL, NULL), -- 제품 2: 대량 입고
    (3, 30, '2024-06-02 09:00:00', 30, NOW(), NOW(), NULL, NULL); -- 제품 3: 회원 ID 1이 생성한 입고 데이터

-- 출고(-) 상황: 재고 감소
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (1, -10, '2024-06-02 14:00:00', 40, NOW(), NOW(), NULL, NULL), -- 제품 1: 10개 출고
    (2, -20, '2024-06-03 11:00:00', 80, NOW(), NOW(), NULL, NULL), -- 제품 2: 20개 출고
    (3, -10, '2024-06-03 15:00:00', 20, NOW(), NOW(), NULL, NULL); -- 제품 3: 10개 출고

-- 재고 유지 상황: 입출고 없이 재고 상태 유지
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (1, 0, '2024-06-04 10:00:00', 40, NOW(), NOW(), NULL, NULL), -- 제품 1: 재고 유지
    (2, 0, '2024-06-04 10:00:00', 80, NOW(), NOW(), NULL, NULL); -- 제품 2: 재고 유지

-- 재고 0 도달: 출고로 재고 소진
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (3, -20, '2024-06-05 10:00:00', 0, NOW(), NOW(), NULL, NULL); -- 제품 3: 재고 0 도달

-- 나머지 7개 상품에 대한 초기 재고
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (4, 20, '2024-06-01 10:00:00', 20, NOW(), NOW(), NULL, NULL),
    (5, 20, '2024-06-01 12:00:00', 20, NOW(), NOW(), NULL, NULL),
    (6, 30, '2024-06-01 12:00:00', 30, NOW(), NOW(), NULL, NULL),
    (7, 10, '2024-06-01 12:00:00', 10, NOW(), NOW(), NULL, NULL),
    (8, 20, '2024-06-01 12:00:00', 20, NOW(), NOW(), NULL, NULL),
    (9, 40, '2024-06-01 12:00:00', 40, NOW(), NOW(), NULL, NULL),
    (10, 10, '2024-06-01 12:00:00', 10, NOW(), NOW(), NULL, NULL);


-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================

-- 테이블 비우기
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE CART;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- V3 Product - CART
-- ==================================================
INSERT INTO CART (
    MEMBER_ID, PRODUCT_ID, QUANTITY, PICKED_DATE, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE
) VALUES
-- 회원 ID 1번, 상품 ID 2번: 장바구니 담기
(1, 2, 2, '2024-07-01 10:00:00', '2024-07-01 10:00:00', NOW(), NULL, NULL),

-- 회원 ID 1번, 상품 ID 9번: 장바구니 담기
(1, 2, 1, '2024-07-01 11:00:00', '2024-07-01 11:00:00', NOW(), NULL, NULL),

-- 회원 ID 2번, 상품 ID 9번: 장바구니 담기
(2, 9, 3, '2024-07-12 09:30:00', '2024-07-12 09:30:00', NOW(), NULL, NULL),

-- 회원 ID 3번, 상품 ID 3번: 장바구니 담기
(3, 3, 1, '2024-07-23 14:15:00', '2024-07-23 14:15:00', NOW(), NULL, NULL),

-- 회원 ID 4번, 상품 ID 2번: 장바구니 담기 및 논리 삭제된 상태
(4, 2, 2, '2024-08-09 16:45:00', '2024-08-09 16:45:00', NOW(), NULL, '2024-08-11 17:40:00'),

-- 회원 ID 2번, 상품 ID 1번: 장바구니 담기
(2, 9, 3, '2024-08-20 11:30:00', '2024-08-20 11:30:00', NOW(), NULL, NULL),

-- 회원 ID 5번, 상품 ID 4번: 장바구니 담기
(5, 1, 1, '2024-08-27 15:35:00', '2024-08-27 15:35:00', NOW(), NULL, NULL),

-- 회원 ID 5번, 상품 ID 2번: 장바구니 담기
(5, 2, 1, '2024-08-29 15:35:00', '2024-08-29 15:35:00', NOW(), NULL, '2024-08-30 22:55:00'),

-- 회원 ID 6번, 상품 ID 2번: 장바구니 담기
(6, 2, 1, '2024-08-29 19:35:00', '2024-07-27 19:35:00', NOW(), NULL, NULL),

-- 회원 ID 7번, 상품 ID 7번: 장바구니 담기
(7, 7, 2, '2024-08-30 22:35:00', '2024-08-30 22:35:00', NOW(), NULL, NULL);

