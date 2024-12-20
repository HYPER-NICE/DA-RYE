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
('john.doe@example.com', 'MEMBER','p123', 'John Doe', 'M', '1990-01-01', '010-1234-5678', 100, FALSE, 0,
 NULL, NOW(), NOW(), NULL, NULL, NULL),

-- 두 번째 사용자
('jane.smith@example.com', 'MEMBER', 'p456', 'Jane Smith', 'F', '1995-05-15', '010-2345-6789', 200, FALSE, 0,
 NULL, NOW(), NOW(), NULL, NULL, NULL),

-- 세 번째 사용자
('alex.kim@example.com', 'MEMBER', 'p789', 'Alex Kim', 'O', '1988-10-10', '010-3456-7890', 50, TRUE, 2,
 '2024-06-01 08:00:00', NOW(), NOW(), NULL, NULL, NULL),

-- 네 번째 사용자
('lisa.park@example.com', 'MEMBER', 'p000', 'Lisa Park', 'F', '2000-12-25', '010-4567-8901', 300, FALSE, 1,
 '2024-06-02 10:00:00', NOW(), NOW(), NULL, NULL, NULL),

-- 다섯 번째 사용자
('mike.lee@example.com', 'ADMIN','p999', 'Mike Lee', 'M', '1992-07-20', '010-5678-9012', 0, FALSE, 3,
 '2024-06-02 15:00:00', NOW(), NOW(), NULL, NULL, NULL);
select * from MEMBER;

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

select * from PRODUCT;
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
(7, 2, '세작', NULL, '신선하고 향긋한 어린 잎으로 만든 녹차', NULL,
 '녹차 잎의 부드러운 향과 깔끔한 맛을 즐길 수 있습니다.', 20000, '오설록농장', '2025-12-31 23:59:59', '녹차 100%', '고온에서 장시간 보관 금지', '해당사항없음',
 '2024-06-01 09:00:00', 50, 'g', 100, NOW(), NULL, NULL, NULL),

(7, 2, '우전', NULL, '첫물차로 만든 고급 녹차', NULL,
 '봄의 첫 잎을 따서 만든 귀한 차입니다. 깊은 녹차의 풍미가 특징입니다.', 35000, '오설록농장', '2025-12-31 23:59:59', '녹차 100%', '습기 및 직사광선 피하기', '해당사항없음',
 '2024-06-01 09:00:00', 40, 'g', 50, NOW(), NULL, NULL, NULL),

(8, 1, '제주 순수 녹차', NULL, '제주의 자연을 담은 프리미엄 녹차', NULL,
 '신선한 제주 녹차 잎으로 만든 고급 녹차입니다.', 22000, '오설록농장', '2025-12-31 23:59:59', '녹차 100%', '습기와 직사광선을 피하세요.', '해당사항없음',
 '2024-06-01 09:00:00', 60, 'g', 90, NOW(), NULL, NULL, NULL),

-- 발효차 상품들
(5, 2, '청우롱', NULL, '깊고 진한 발효차의 풍미', NULL,
 '제주의 자연에서 발효된 차로 부드럽고 깊은 맛이 특징입니다.', 30000, '오설록농장', '2025-12-31 23:59:59', '발효차 100%', '고온 다습한 환경 피하기', '해당사항없음',
 '2024-06-01 09:00:00', 100, 'g', 80, NOW(), NULL, NULL, NULL),

(5, 4, '제주 화산암차', NULL, '발효 과정을 거쳐 깊은 맛을 낸 발효차', NULL,
 '시간을 두고 발효시킨 부드럽고 진한 맛의 발효차입니다.', 32000, '오설록농장', '2025-12-31 23:59:59', '발효차 100%', '직사광선에 노출하지 마세요.', '해당사항없음',
 '2024-06-01 09:00:00', 80, 'g', 70, NOW(), NULL, NULL, NULL),

-- 블렌디드 티 상품들
(10, 4, '달빛걷기', NULL, '제주 밤바다가 떠오르는 후발효차', NULL,
 '달큰한 배향이 달빛처럼 은은하게 감도는 차입니다.', 15000, '오설록농장', '2025-12-31 23:59:59', '후발효차(제주산) 80%, 캔디류(별사탕) 10%, 돌배(국산) 10%', '고온 다습한 곳을 피하고 개봉한 차는 밀봉하여 건냉한 곳에 보관하십시오.', '해당사항없음',
 '2024-06-01 09:00:00', 35, 'g', 70, NOW(), NULL, NULL, NULL),

(10, 4, '삼다연 제주영귤', NULL, '싱그러운 귤꽃향 가득한 블렌디드 티', NULL,
 '우아한 귤꽃향과 상큼한 제주 영귤이 조화롭게 블렌딩된 차입니다.', 25000, '오설록농장', '2025-12-31 23:59:59', '발효차(제주산) 95%, 천연향료(영귤향) 5%', '고온다습한 곳을 피하고 개봉 후 밀봉하여 실온보관하십시오.', '해당사항없음',
 '2024-06-01 09:00:00', 40, 'g', 70, NOW(), NULL, NULL, NULL),

(11, 4, '귤꽃향 블라썸 티', NULL, '싱그러운 귤꽃향 가득한 블렌디드 티', NULL,
 '우아한 귤꽃향과 상큼한 제주 영귤이 조화롭게 블렌딩된 차입니다.', 25000, '오설록농장', '2025-12-31 23:59:59', '영귤 90%, 귤피 10%', '장시간 보관 시 향이 약해질 수 있습니다.', '해당사항없음',
 '2024-06-01 09:00:00', 30, 'g', 70, NOW(), NULL, NULL, NULL),

(11, 2, '허니 레몬 티', NULL, '달콤한 꿀과 상큼한 레몬의 조화', NULL,
 '레몬과 꿀이 어우러져 상큼하고 달콤한 맛을 선사합니다.', 18000, '오설록농장', '2025-12-31 23:59:59', '레몬농축액, 꿀', '직사광선 피하고 서늘한 곳 보관', '해당사항없음',
 '2024-06-01 09:00:00', 100, 'ml', 120, NOW(), NULL, NULL, NULL),

(11, 2, '트로피컬 블랙티', NULL, '달콤한 망고와 복숭아향이 가득한 열대과일 홍차', NULL,
 '제주산 고급 홍차에 망고가 어우러져 달콤하고 기분까지 좋아지는 차입니다.', 18000, '오설록농장', '2025-12-31 23:59:59', '녹차(제주산) 70%, 홍차(제주산) 20%, 후발효차(제주산) 10%, 향료(복숭아향) 6%, 망고과즙분말 4%', '직사광선 피하고 서늘한 곳 보관고온 다습한 곳을 피하고 개봉한 차는 밀봉하여 건냉한 곳에 보관하십시오.', '해당사항없음',
 '2024-06-01 09:00:00', 30, 'g', 120, NOW(), NULL, NULL, NULL),

-- 티웨어 상품들
(13, 3, '유리 찻잔 세트', NULL, '고급스러운 유리 찻잔과 받침 세트', NULL,
 '티타임을 더욱 특별하게 만들어 줄 유리 찻잔 세트입니다.', 40000, '오설록', '2026-12-31 23:59:59', '유리 100%', '충격 주의, 깨질 수 있음', '해당사항없음',
 '2024-06-01 09:00:00', 2, '개', 30, NOW(), NULL, NULL, NULL),

(14, 2, '프리미엄 티포트 세트', NULL, '티타임의 품격을 높여주는 프리미엄 티포트 세트', NULL,
 '유리와 스테인리스 소재로 제작된 고급스러운 티포트 세트입니다.', 60000, '인소일', '2026-12-31 23:59:59', '유리, 스테인리스', '충격에 주의하세요.', '해당사항없음',
 '2024-06-01 09:00:00', 1, '세트', 20, NOW(), NULL, NULL, NULL),

(15, 2, '탁가온 고유 오리진 타원접시', NULL, '우리 전통 그릇의 소재와 모양을 독특한 시각으로 표현된 컬렉션', NULL,
 '오랜 연구 끝에 탄생한 탁가온만의 유약은 숙련된 장인의 수작업을 통해 전통 방식으로 시유 되며 1300도의 소성 과정을 거치면서 고유 식기만의 부드러운 광택과 특유의 깊이감을 선사합니다.', 60000, '탁가온', '2026-12-31 23:59:59', '세라믹', '충격에 주의하세요.', '해당사항없음',
 '2024-06-10 10:00:00', 1, '세트', 20, NOW(), NULL, NULL, NULL),

-- 선물 세트
(16, 2, '티 베스트 셀렉션', NULL, '오설록의 인기 티를 모은 선물 세트', NULL,
 '다양한 차를 경험할 수 있는 베스트 셀렉션 세트입니다.', 55000, '오설록농장', '2025-12-31 23:59:59', '녹차(제주산), 향료(스윗부케향), 마리골드 (이집트산)', '습기 및 직사광선 피하기', '해당사항없음',
 '2024-06-01 09:00:00', 120, 'g', 50, NOW(), NULL, NULL, NULL),

(17, 2, '티 에디션 헤리티지', NULL, '오설록 전통 차를 모은 선물 세트', NULL,
 '헤리티지를 품은 마스터즈 티와 감각적인 블렌딩의 티 에디션 세트입니다.', 55000, '오설록농장', '2025-12-31 23:59:59', '유기농 녹차(제주산), 유기농 반발효차(제주산)', '습기 및 직사광선 피하기', '해당사항없음',
 '2024-06-01 09:00:00', 135, 'g', 50, NOW(), NULL, NULL, NULL);

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
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (1, 50, 'IN','2024-06-01 10:00:00', 50, NOW(), NULL, NULL, NULL), -- 제품 1: 입고
    (2, 100, 'IN', '2024-06-01 12:00:00', 100, NOW(), NULL, NULL, NULL), -- 제품 2: 입고
    (3, 30, 'IN', '2024-06-02 09:00:00', 30, NOW(), NULL, NULL, NULL), -- 제품 3: 입고
    (4, 5, 'IN', '2024-06-01 12:00:00', 5, NOW(), NULL, NULL, NULL), -- 제품 4: 입고
    (2, 2, 'IN-CANCEL', '2024-06-02 09:00:00', 102, NOW(), NULL, NULL, NULL); -- 제품 2: 취소제품 입고

-- 출고(-) 상황: 재고 감소
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (1, -10, 'OUT-RETURN', '2024-06-02 14:00:00', 40, NOW(), NULL, NULL, NULL), -- 제품 1: 10개 반환
    (2, -2, 'OUT-ORDER', '2024-06-03 11:00:00', 100, NOW(), NULL, NULL, NULL), -- 제품 2: 2개 주문
    (3, -1, 'OUT-ORDER', '2024-06-03 15:00:00', 29, NOW(), NULL, NULL, NULL), -- 제품 3: 1개 주문
    (4, -1, 'OUT-ORDER', '2024-06-01 12:00:00', 4, NOW(), NULL, NULL, NULL), -- 제품 4: 1개 주문
    (4, -2, 'OUT-DAMAGE', '2024-06-01 12:00:00', 2, NOW(), NULL, NULL, NULL), -- 제품 4: 2개 파손
    (4, -1, 'OUT-RETURN', '2024-06-01 12:00:00', 1, NOW(), NULL, NULL, NULL), -- 제품 4: 1개 반환
    (4, -1, 'OUT-ORDER', '2024-06-01 12:00:00', 0, NOW(), NULL, NULL, NULL); -- 제품 4: 1개 주문, 재고 0 도달

-- 재고 유지 상황: 입출고 없이 재고 상태 유지
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (1, 0, 'CHECK','2024-06-04 10:00:00', 40, NOW(), NULL, NULL, NULL), -- 제품 1: 재고 유지
    (2, 0, 'CHECK','2024-06-04 10:00:00', 100, NOW(), NULL, NULL, NULL); -- 제품 2: 재고 유지

-- 나머지 6개 상품에 대한 초기 재고
INSERT INTO STOCK (PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, STOCK_INOUT_DATE, CURRENT_STOCK, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES
    (5, 20, 'IN', '2024-06-01 12:00:00', 20, NOW(), NULL, NULL, NULL),
    (6, 30, 'IN','2024-06-01 12:00:00', 30, NOW(), NULL, NULL, NULL),
    (7, 10, 'IN','2024-06-01 12:00:00', 10, NOW(), NULL, NULL, NULL),
    (8, 20, 'IN','2024-06-01 12:00:00', 20, NOW(), NULL, NULL, NULL),
    (9, 40, 'IN','2024-06-01 12:00:00', 40, NOW(), NULL, NULL, NULL),
    (10, 10, 'IN','2024-06-01 12:00:00', 10, NOW(), NULL, NULL, NULL);

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



-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================

-- 테이블 비우기
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE ORDER_MAIN;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- V4 Order - ORDER_MAIN
-- ==================================================
INSERT INTO ORDER_MAIN (
    MEMBER_ID,
    ORDER_DATE,
    CREATED_DATE,
    LAST_MODIFIED_DATE,
    LAST_MODIFIED_MEMBER
)
VALUES
    (1, '2024-10-01 10:03:00', '2024-10-01 10:03:00', NULL, NULL),
    (2, '2024-10-02 14:32:00', '2024-10-02 14:32:00', NULL, NULL),
    (3, '2024-10-03 16:45:00', '2024-10-03 16:45:00', NULL, NULL),
    (3, '2024-11-25 09:02:00', '2024-11-25 09:02:00', NULL, NULL),
    (1, '2024-11-25 09:18:00', '2024-11-25 09:18:00', NULL, NULL);



-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================

-- 테이블 비우기
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE ORDER_DETAIL;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- V4 Order - ORDER_DETAIL ??? quantity랑 unit_price가 왜 있냐? 전체 주문 금액 계산 필요함..
-- ==================================================
-- ORDER_DETAIL 테이블 더미 데이터 삽입
INSERT INTO ORDER_DETAIL (
    ORDER_ID,
    PRODUCT_ID,
    QUANTITY,
    UNIT_PRICE,
    CREATED_DATE,
    LAST_MODIFIED_DATE,
    LAST_MODIFIED_MEMBER
)
VALUES
-- 주문 1번: 단일 제품 주문
(1, 1, 2, 5000, '2024-12-01 10:00:00', '2024-12-01 10:00:00', NULL),

-- 주문 1번: 여러 제품 포함
(1, 102, 1, 15000, '2024-12-01 10:05:00', '2024-12-01 10:05:00', NULL),

-- 주문 2번: 다량 구매
(2, 103, 10, 1000, '2024-12-02 12:30:00', '2024-12-02 12:30:00', 2),

-- 주문 3번: 고가 제품 주문
(3, 104, 1, 200000, '2024-12-03 14:45:00', '2024-12-03 14:45:00', 3),

-- 주문 4번: 수량과 가격이 다양한 제품
(4, 105, 3, 7000, '2024-12-04 09:00:00', '2024-12-04 09:00:00', NULL),
(4, 106, 5, 3000, '2024-12-04 09:10:00', '2024-12-04 09:10:00', 1),

-- 주문 5번: 최근 생성된 주문
(5, 107, 2, 12000, NOW(), NOW(), 4);



-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================

-- 테이블 비우기
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE ORDER_DELIVERY_MAIN;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- V4 Order - ORDER_DELIVERY_MAIN
-- ==================================================
INSERT INTO ORDER_DELIVERY_MAIN (
    ORDER_MAIN_ID,
    DELIVERY_COMPANY_NAME,
    TRACKING_NUMBER,
    DELIVERY_REQUEST_NOTE,
    CREATED_DATE,
    LAST_MODIFIED_DATE,
    LAST_MODIFIED_MEMBER
)
VALUES
    (1, 'CJ대한통운', 'CJ123333789', '부재 시 경비실에 맡겨 주세요.', NOW(), NULL, NULL),
    (2, '한진택배', 'HJ987654321', NULL, NOW(), NULL, NULL),
    (3, 'CJ대한통운', 'CJ12345678', '빠른 배송 부탁드립니다.', NOW(), NULL, NULL),
    (4, '우체국택배', 'TRACKNUM12345678901234567890', '오전 9시 이전에 배송 부탁드립니다.', NOW(), NULL, NULL),
    (5, '롯데택배', 'LOTT12345678', NULL, NOW(), NULL, NULL);


-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================

-- 테이블 비우기
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE ORDER_DELIVERY_STATUS;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- V4 Order - ORDER_DELIVERY_STATUS
-- ==================================================
INSERT INTO ORDER_DELIVERY_STATUS (
    ORDER_DELIVERY_MAIN_ID,
    DELIVERY_STATUS_CODE_ID,
    DELIVERY_DATE,
    CREATED_DATE,
    LAST_MODIFIED_DATE,
    LAST_MODIFIED_MEMBER
)
VALUES
-- 주문 ID 1번: 배송 전 상태
(1, 1, '2024-12-01 09:00:00', '2024-12-01 09:00:00', NULL, NULL),

-- 주문 ID 1번: 배송 중 상태
(1, 2, '2024-12-01 15:00:00', '2024-12-01 15:00:00', NULL, NULL),

-- 주문 ID 1번: 배송 완료 상태
(1, 3, '2024-12-02 18:00:00', '2024-12-02 18:00:00', NULL, NULL),

-- 주문 ID 2번: 배송 전 상태
(2, 1, '2024-12-03 10:00:00', '2024-12-03 10:00:00', NULL, NULL),

-- 주문 ID 2번: 배송 중 상태
(2, 2, '2024-12-03 16:00:00', '2024-12-03 16:00:00', NULL, NULL),

-- 주문 ID 2번: 배송 완료 상태
(2, 3, '2024-12-04 12:00:00', '2024-12-04 12:00:00', NULL, NULL);
