-- ==================================================
-- MEMBER
-- 의존성
-- 없음
-- ==================================================
INSERT INTO MEMBER (
    EMAIL, ROLE, PASSWORD, NAME, SEX, BIRTHDATE, CONTACT, POINT, LOCKED, PW_FAILED_COUNT,
    LATEST_LOGIN_DATE, REG_DATE
) VALUES
      ('root@darye.dev', 'ADMIN', '$2a$10$SS/aWir9xcxfp2NSvr5Hse9Abee4h/pjsoKwGEfFAW3N0XIRDP8pe', 'Root', 'M', '1990-01-01', '010-1234-5678', 0, FALSE, 0, NULL, NOW()),
      ('john.doe@example.com', 'USER', '$2a$10$SS/aWir9xcxfp2NSvr5Hse9Abee4h/pjsoKwGEfFAW3N0XIRDP8pe', 'John Doe', 'M', '1990-01-01', '010-1234-5678', 100, FALSE, 0, NULL, NOW()),
      ('jane.smith@example.com', 'USER', '$2a$10$SS/aWir9xcxfp2NSvr5Hse9Abee4h/pjsoKwGEfFAW3N0XIRDP8pe', 'Jane Smith', 'F', '1995-05-15', '010-2345-6789', 200, FALSE, 0, NULL, NOW()),
      ('alex.kim@example.com', 'USER', '$2a$10$SS/aWir9xcxfp2NSvr5Hse9Abee4h/pjsoKwGEfFAW3N0XIRDP8pe', 'Alex Kim', 'O', '1988-10-10', '010-3456-7890', 50, TRUE, 2, '2024-06-01 08:00:00', NOW()),
      ('lisa.park@example.com', 'USER', '$2a$10$SS/aWir9xcxfp2NSvr5Hse9Abee4h/pjsoKwGEfFAW3N0XIRDP8pe', 'Lisa Park', 'F', '2000-12-25', '010-4567-8901', 300, FALSE, 1, '2024-06-02 10:00:00', NOW());

-- ==================================================
-- CATEGORY
-- 의존성
-- 없음
-- ==================================================
-- 최상위 카테고리 (1단계)
INSERT INTO CATEGORY (
    PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION
) VALUES
      (NULL, 1, '티 제품', '오설록의 다양한 차 제품을 소개합니다.'),
      (NULL, 2, '티웨어', '차를 즐기기 위한 다양한 티웨어.'),
      (NULL, 3, '선물세트', '소중한 사람에게 전하는 선물 세트.');

-- 티 제품 하위 카테고리 (2단계)
INSERT INTO CATEGORY (
    PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION
) VALUES
      (1, 1, '녹차', '신선하고 깔끔한 녹차 제품.'),
      (1, 2, '발효차', '깊고 진한 풍미의 발효차 제품.'),
      (1, 3, '블렌디드 티', '다양한 재료로 블렌딩된 차 제품.');

-- 녹차 하위 카테고리 (3단계)
INSERT INTO CATEGORY (
    PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION
) VALUES
      (4, 1, '잎차', '전통적인 방식으로 우려내는 녹차 잎차.'),
      (4, 2, '티백', '간편하게 즐길 수 있는 녹차 티백.'),
      (4, 3, '파우더', '녹차 가루로 다양한 음료에 활용.');

-- 블렌디드 티 하위 카테고리 (3단계)
INSERT INTO CATEGORY (
    PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION
) VALUES
      (6, 1, '잎차', '다양한 재료를 블렌딩한 잎차.'),
      (6, 2, '티백', '간편하게 즐기는 블렌디드 티 티백.'),
      (6, 3, '파우더', '블렌디드 티 파우더, 달빛걷기 등 특별한 맛.');

-- 티웨어 하위 카테고리 (2단계)
INSERT INTO CATEGORY (
    PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION
) VALUES
      (2, 1, '찻잔', '다양한 디자인의 찻잔.'),
      (2, 2, '티포트', '차를 우리는 티포트 제품.'),
      (2, 3, '차도구', '차를 준비하는 데 필요한 다양한 도구.');

-- 선물세트 하위 카테고리 (2단계)
INSERT INTO CATEGORY (
    PARENT_ID, SCREEN_ORDER, NAME, DESCRIPTION
) VALUES
      (3, 1, '베스트셀러 세트', '가장 인기 있는 제품을 묶은 세트.'),
      (3, 2, '프리미엄 세트', '고급 제품으로 구성된 특별한 선물 세트.');

-- ========================================================
-- PRODUCT
-- 의존성
-- CATEGORY
-- ========================================================
INSERT INTO PRODUCT (
    CATEGORY_ID, PRODUCT_STATUS_CODE_ID, NAME, THUMBNAIL_IMAGE, SHORT_DESCRIPTION, DESCRIPTION_IMAGE,
    LONG_DESCRIPTION, PRICE, MANUFACTURER, EXPIRATION_DATE, INGREDIENTS, PRECAUTIONS, IMPORTER,
    SALE_DATE, CAPACITY, UNIT, QUANTITY
) VALUES
      -- 녹차 상품들
      (7, 2, '세작', NULL, '신선하고 향긋한 어린 잎으로 만든 녹차', NULL,
       '녹차 잎의 부드러운 향과 깔끔한 맛을 즐길 수 있습니다.', 20000, '오설록농장', '2025-12-31 23:59:59', '녹차 100%', '고온에서 장시간 보관 금지', '해당사항없음',
       '2024-06-01 09:00:00', 50, 'g', 100),
      (7, 2, '우전', NULL, '첫물차로 만든 고급 녹차', NULL,
       '봄의 첫 잎을 따서 만든 귀한 차입니다. 깊은 녹차의 풍미가 특징입니다.', 35000, '오설록농장', '2025-12-31 23:59:59', '녹차 100%', '습기 및 직사광선 피하기', '해당사항없음',
       '2024-06-01 09:00:00', 40, 'g', 50),
      (8, 1, '제주 순수 녹차', NULL, '제주의 자연을 담은 프리미엄 녹차', NULL,
       '신선한 제주 녹차 잎으로 만든 고급 녹차입니다.', 22000, '오설록농장', '2025-12-31 23:59:59', '녹차 100%', '습기와 직사광선을 피하세요.', '해당사항없음',
       '2024-06-01 09:00:00', 60, 'g', 90),

      -- 발효차 상품들
      (5, 2, '청우롱', NULL, '깊고 진한 발효차의 풍미', NULL,
       '제주의 자연에서 발효된 차로 부드럽고 깊은 맛이 특징입니다.', 30000, '오설록농장', '2025-12-31 23:59:59', '발효차 100%', '고온 다습한 환경 피하기', '해당사항없음',
       '2024-06-01 09:00:00', 100, 'g', 80),
      (5, 4, '제주 화산암차', NULL, '발효 과정을 거쳐 깊은 맛을 낸 발효차', NULL,
       '시간을 두고 발효시킨 부드럽고 진한 맛의 발효차입니다.', 32000, '오설록농장', '2025-12-31 23:59:59', '발효차 100%', '직사광선에 노출하지 마세요.', '해당사항없음',
       '2024-06-01 09:00:00', 80, 'g', 70),

      -- 블렌디드 티 상품들
      (10, 4, '달빛걷기', NULL, '제주 밤바다가 떠오르는 후발효차', NULL,
       '달큰한 배향이 달빛처럼 은은하게 감도는 차입니다.', 15000, '오설록농장', '2025-12-31 23:59:59', '후발효차(제주산) 80%, 캔디류(별사탕) 10%, 돌배(국산) 10%',
       '고온 다습한 곳을 피하고 개봉한 차는 밀봉하여 건냉한 곳에 보관하십시오.', '해당사항없음',
       '2024-06-01 09:00:00', 35, 'g', 70),
      (10, 4, '삼다연 제주영귤', NULL, '싱그러운 귤꽃향 가득한 블렌디드 티', NULL,
       '우아한 귤꽃향과 상큼한 제주 영귤이 조화롭게 블렌딩된 차입니다.', 25000, '오설록농장', '2025-12-31 23:59:59', '발효차(제주산) 95%, 천연향료(영귤향) 5%',
       '고온다습한 곳을 피하고 개봉 후 밀봉하여 실온보관하십시오.', '해당사항없음',
       '2024-06-01 09:00:00', 40, 'g', 70),
      (11, 4, '귤꽃향 블라썸 티', NULL, '싱그러운 귤꽃향 가득한 블렌디드 티', NULL,
       '우아한 귤꽃향과 상큼한 제주 영귤이 조화롭게 블렌딩된 차입니다.', 25000, '오설록농장', '2025-12-31 23:59:59', '영귤 90%, 귤피 10%',
       '장시간 보관 시 향이 약해질 수 있습니다.', '해당사항없음',
       '2024-06-01 09:00:00', 30, 'g', 70),
      (11, 2, '허니 레몬 티', NULL, '달콤한 꿀과 상큼한 레몬의 조화', NULL,
       '레몬과 꿀이 어우러져 상큼하고 달콤한 맛을 선사합니다.', 18000, '오설록농장', '2025-12-31 23:59:59', '레몬농축액, 꿀', '직사광선 피하고 서늘한 곳 보관', '해당사항없음',
       '2024-06-01 09:00:00', 100, 'ml', 120),
      (11, 2, '트로피컬 블랙티', NULL, '달콤한 망고와 복숭아향이 가득한 열대과일 홍차', NULL,
       '제주산 고급 홍차에 망고가 어우러져 달콤하고 기분까지 좋아지는 차입니다.', 18000, '오설록농장', '2025-12-31 23:59:59',
       '녹차(제주산) 70%, 홍차(제주산) 20%, 후발효차(제주산) 10%, 향료(복숭아향) 6%, 망고과즙분말 4%',
       '직사광선 피하고 서늘한 곳 보관고온 다습한 곳을 피하고 개봉한 차는 밀봉하여 건냉한 곳에 보관하십시오.', '해당사항없음',
       '2024-06-01 09:00:00', 30, 'g', 120),

      -- 티웨어 상품들
      (13, 3, '유리 찻잔 세트', NULL, '고급스러운 유리 찻잔과 받침 세트', NULL,
       '티타임을 더욱 특별하게 만들어 줄 유리 찻잔 세트입니다.', 40000, '오설록', '2026-12-31 23:59:59', '유리 100%', '충격 주의, 깨질 수 있음', '해당사항없음',
       '2024-06-01 09:00:00', 2, '개', 30),
      (14, 2, '프리미엄 티포트 세트', NULL, '티타임의 품격을 높여주는 프리미엄 티포트 세트', NULL,
       '유리와 스테인리스 소재로 제작된 고급스러운 티포트 세트입니다.', 60000, '인소일', '2026-12-31 23:59:59', '유리, 스테인리스', '충격에 주의하세요.', '해당사항없음',
       '2024-06-01 09:00:00', 1, '세트', 20),
      (15, 2, '탁가온 고유 오리진 타원접시', NULL, '우리 전통 그릇의 소재와 모양을 독특한 시각으로 표현된 컬렉션', NULL,
       '오랜 연구 끝에 탄생한 탁가온만의 유약은 숙련된 장인의 수작업을 통해 전통 방식으로 시유 되며 1300도의 소성 과정을 거치면서 고유 식기만의 부드러운 광택과 특유의 깊이감을 선사합니다.', 60000,
       '탁가온', '2026-12-31 23:59:59', '세라믹', '충격에 주의하세요.', '해당사항없음',
       '2024-06-10 10:00:00', 1, '세트', 20),

      -- 선물 세트
      (16, 2, '티 베스트 셀렉션', NULL, '오설록의 인기 티를 모은 선물 세트', NULL,
       '다양한 차를 경험할 수 있는 베스트 셀렉션 세트입니다.', 55000, '오설록농장', '2025-12-31 23:59:59', '녹차(제주산), 향료(스윗부케향), 마리골드 (이집트산)',
       '습기 및 직사광선 피하기', '해당사항없음',
       '2024-06-01 09:00:00', 120, 'g', 50),
      (17, 2, '티 에디션 헤리티지', NULL, '오설록 전통 차를 모은 선물 세트', NULL,
       '헤리티지를 품은 마스터즈 티와 감각적인 블렌딩의 티 에디션 세트입니다.', 55000, '오설록농장', '2025-12-31 23:59:59', '유기농 녹차(제주산), 유기농 반발효차(제주산)',
       '습기 및 직사광선 피하기', '해당사항없음',
       '2024-06-01 09:00:00', 135, 'g', 50);

-- ========================================================
-- STOCK
-- 의존성
-- MEMBER, PRODUCT
-- ========================================================
-- 입고(+) 상황: 재고 추가
INSERT INTO STOCK (
    PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, STOCK_INOUT_DATE, CURRENT_STOCK
) VALUES
      (1, 50, 'IN', '2024-06-01 10:00:00', 50),   -- 제품 1: 입고
      (2, 100, 'IN', '2024-06-01 12:00:00', 100), -- 제품 2: 입고
      (3, 30, 'IN', '2024-06-02 09:00:00', 30),   -- 제품 3: 입고
      (4, 5, 'IN', '2024-06-01 12:00:00', 5),     -- 제품 4: 입고
      (2, 2, 'IN-CANCEL', '2024-06-02 09:00:00', 102); -- 제품 2: 취소제품 입고

-- 출고(-) 상황: 재고 감소
INSERT INTO STOCK (
    PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, STOCK_INOUT_DATE, CURRENT_STOCK
) VALUES
      (1, -10, 'OUT-RETURN', '2024-06-02 14:00:00', 40), -- 제품 1: 10개 반환
      (2, -2, 'OUT-ORDER', '2024-06-03 11:00:00', 100),  -- 제품 2: 2개 주문
      (3, -1, 'OUT-ORDER', '2024-06-03 15:00:00', 29),   -- 제품 3: 1개 주문
      (4, -1, 'OUT-ORDER', '2024-06-01 12:00:00', 4),    -- 제품 4: 1개 주문
      (4, -2, 'OUT-DAMAGE', '2024-06-01 12:00:00', 2),   -- 제품 4: 2개 파손
      (4, -1, 'OUT-RETURN', '2024-06-01 12:00:00', 1),   -- 제품 4: 1개 반환
      (4, -1, 'OUT-ORDER', '2024-06-01 12:00:00', 0);    -- 제품 4: 1개 주문, 재고 0 도달

-- 재고 유지 상황: 입출고 없이 재고 상태 유지
INSERT INTO STOCK (
    PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, STOCK_INOUT_DATE, CURRENT_STOCK
) VALUES
      (1, 0, 'CHECK', '2024-06-04 10:00:00', 40), -- 제품 1: 재고 유지
      (2, 0, 'CHECK', '2024-06-04 10:00:00', 100); -- 제품 2: 재고 유지

-- 나머지 6개 상품에 대한 초기 재고
INSERT INTO STOCK (
    PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, STOCK_INOUT_DATE, CURRENT_STOCK
) VALUES
      (5, 20, 'IN', '2024-06-01 12:00:00', 20),
      (6, 30, 'IN', '2024-06-01 12:00:00', 30),
      (7, 10, 'IN', '2024-06-01 12:00:00', 10),
      (8, 20, 'IN', '2024-06-01 12:00:00', 20),
      (9, 40, 'IN', '2024-06-01 12:00:00', 40),
      (10, 10, 'IN', '2024-06-01 12:00:00', 10);

-- ==================================================
-- CART
-- 의존성
-- MEMBER, PRODUCT
-- ==================================================
INSERT INTO CART (
    MEMBER_ID, PRODUCT_ID, QUANTITY, PICKED_DATE
) VALUES
      -- 회원 ID 1번, 상품 ID 2번: 장바구니 담기
      (1, 2, 2, '2024-07-01 10:00:00'),

      -- 회원 ID 1번, 상품 ID 3번: 장바구니 담기
      (1, 3, 1, '2024-07-01 11:00:00'),

      -- 회원 ID 2번, 상품 ID 9번: 장바구니 담기
      (2, 9, 3, '2024-07-12 09:30:00'),

      -- 회원 ID 3번, 상품 ID 3번: 장바구니 담기
      (3, 3, 1, '2024-07-23 14:15:00'),

      -- 회원 ID 4번, 상품 ID 2번: 장바구니 담기
      (4, 2, 2, '2024-08-09 16:45:00'),

      -- 회원 ID 2번, 상품 ID 2번: 장바구니 담기
      (2, 2, 3, '2024-08-20 11:30:00'),

      -- 회원 ID 5번, 상품 ID 4번: 장바구니 담기
      (5, 1, 1, '2024-08-27 15:35:00'),

      -- 회원 ID 5번, 상품 ID 2번: 장바구니 담기
      (5, 2, 1, '2024-08-29 15:35:00'),

      -- 회원 ID 2번, 상품 ID 3번: 장바구니 담기
      (2, 3, 2, '2024-09-19 15:35:00'),

      -- 회원 ID 1번, 상품 ID 4번: 장바구니 담기
      (1, 4, 1, '2024-09-29 15:35:00');

-- ==================================================
-- ORDER_MAIN
-- 의존성
-- 없음
-- ==================================================
INSERT INTO ORDER_MAIN (
    MEMBER_ID, ORDER_DATE
) VALUES
      (1, '2024-10-01 10:03:00'),
      (2, '2024-10-02 14:32:00'),
      (3, '2024-10-03 16:45:00'),
      (3, '2024-11-25 09:02:00'),
      (1, '2024-11-25 09:18:00');

-- ==================================================
-- ORDER_DETAIL
-- 의존성
-- ORDER_MAIN, PRODUCT
-- ==================================================
INSERT INTO ORDER_DETAIL (
    ORDER_ID, PRODUCT_ID, QUANTITY, UNIT_PRICE
) VALUES
      -- 주문 1번: 단일 제품 주문
      (1, 10, 2, 5000),

      -- 주문 1번: 여러 제품 포함
      (1, 1, 1, 15000),

      -- 주문 2번: 다량 구매
      (2, 2, 10, 1000),

      -- 주문 3번: 고가 제품 주문
      (3, 3, 1, 200000),

      -- 주문 4번: 수량과 가격이 다양한 제품
      (4, 4, 3, 7000),
      (4, 5, 5, 3000),

      -- 주문 5번: 최근 생성된 주문
      (5, 6, 2, 12000);



-- ==================================================
-- ORDER_DELIVERY_MAIN
-- 의존성
-- ORDER_MAIN
-- ==================================================
INSERT INTO ORDER_DELIVERY_MAIN (
    ORDER_MAIN_ID, DELIVERY_COMPANY_NAME, TRACKING_NUMBER, DELIVERY_REQUEST_NOTE
) VALUES
      (1, 'CJ대한통운', 'CJ123333789', '부재 시 경비실에 맡겨 주세요.'),
      (2, '한진택배', 'HJ987654321', NULL),
      (3, 'CJ대한통운', 'CJ12345678', '빠른 배송 부탁드립니다.'),
      (4, '우체국택배', 'TRACKNUM12345678901234567890', '오전 9시 이전에 배송 부탁드립니다.'),
      (5, '롯데택배', 'LOTT12345678', NULL);

-- ==================================================
-- ORDER_DELIVERY_STATUS
-- 의존성
-- ORDER_DELIVERY_MAIN
-- ==================================================
INSERT INTO ORDER_DELIVERY_STATUS (
    ORDER_DELIVERY_MAIN_ID, DELIVERY_STATUS_CODE_ID, DELIVERY_DATE
) VALUES
      -- 주문 ID 1번: 배송 전 상태
      (1, 1, '2024-12-01 09:00:00'),

      -- 주문 ID 1번: 배송 중 상태
      (1, 2, '2024-12-01 15:00:00'),

      -- 주문 ID 1번: 배송 완료 상태
      (1, 3, '2024-12-02 18:00:00'),

      -- 주문 ID 2번: 배송 전 상태
      (2, 1, '2024-12-03 10:00:00'),

      -- 주문 ID 2번: 배송 중 상태
      (2, 2, '2024-12-03 16:00:00'),

      -- 주문 ID 2번: 배송 완료 상태
      (2, 3, '2024-12-04 12:00:00');



-- ==================================================
-- ORDER_PAYMENT_MAIN
-- 의존성
-- ORDER_MAIN
-- ==================================================
INSERT INTO ORDER_PAYMENT_MAIN (
    ORDER_ID, TOTAL_AMOUNT
) VALUES
      (1, 20000),  -- 주문 ID 1번: 총 결제 금액 20,000원
      (2, 35000),  -- 주문 ID 2번: 총 결제 금액 35,000원
      (3, 22000),  -- 주문 ID 3번: 총 결제 금액 22,000원
      (4, 15000),  -- 주문 ID 4번: 총 결제 금액 15,000원
      (5, 12000);  -- 주문 ID 5번: 총 결제 금액 12,000원

-- ==================================================
-- ORDER_PAYMENT_DETAIL
-- 의존성
-- ORDER_PAYMENT_MAIN, PAYMENT_METHOD_CODE, PAYMENT_STATUS_CODE
-- ==================================================
INSERT INTO ORDER_PAYMENT_DETAIL (
    ORDER_PAYMENT_MAIN_ID, PAYMENT_METHOD_CODE_ID, ORDER_PAYMENT_STATUS_CODE_ID,
    APPROVAL_NUMBER, PAYMENT_AMOUNT, PAYMENT_DATE, PAYMENT_COMMENT
) VALUES
      -- 주문 결제 1번
      (1, 1, 2, 'APPROVAL12345', 20000, '2024-10-01 10:05:00', '신용카드 결제 완료'),

      -- 주문 결제 2번
      (2, 2, 2, 'APPROVAL67890', 35000, '2024-10-02 14:35:00', '포인트 사용 결제 완료'),

      -- 주문 결제 3번
      (3, 3, 2, 'APPROVAL54321', 22000, '2024-10-03 16:50:00', '무통장 입금 결제 완료'),

      -- 주문 결제 4번
      (4, 4, 2, 'APPROVAL98765', 15000, '2024-11-25 09:20:00', 'HYPER PAY 결제 완료'),

      -- 주문 결제 5번
      (5, 1, 2, 'APPROVAL11223', 12000, '2024-11-25 09:25:00', '신용카드 결제 완료');

-- ==================================================
-- POINT_HISTORY
-- 의존성
-- MEMBER, POINT_TRANSACTION_TYPE
-- ==================================================
-- 포인트 적립 기록
INSERT INTO POINT_HISTORY (
    MEMBER_ID, POINT_TRANSACTION_TYPE_ID, ORDER_MAIN_ID, AMOUNT, DESCRIPTION, CREATED_DATE
) VALUES
      (1, 1, 1, 100, '첫 주문 적립', NOW()),        -- 회원 1, 주문 1, 포인트 적립
      (2, 1, 2, 150, '주문 포인트 적립', NOW()),    -- 회원 2, 주문 2, 포인트 적립
      (3, 1, 3, 200, '주문 후 포인트 적립', NOW()), -- 회원 3, 주문 3, 포인트 적립
      (4, 1, 4, 300, '회원 가입 포인트 지급', NOW()); -- 회원 4, 첫 가입 포인트 적립

-- 포인트 사용 기록
INSERT INTO POINT_HISTORY (
    MEMBER_ID, POINT_TRANSACTION_TYPE_ID, ORDER_MAIN_ID, AMOUNT, DESCRIPTION, CREATED_DATE
) VALUES
      (1, 2, 1, -50, '첫 주문 포인트 사용', NOW()),    -- 회원 1, 주문 1, 포인트 사용
      (2, 2, 2, -100, '제품 구매 시 포인트 사용', NOW()), -- 회원 2, 주문 2, 포인트 사용
      (3, 2, 3, -150, '고가 제품 구매 시 사용', NOW()),  -- 회원 3, 주문 3, 포인트 사용
      (4, 2, 4, -200, '프리미엄 제품 구매', NOW());    -- 회원 4, 주문 4, 포인트 사용

-- 포인트 소멸 기록
INSERT INTO POINT_HISTORY (
    MEMBER_ID, POINT_TRANSACTION_TYPE_ID, ORDER_MAIN_ID, AMOUNT, DESCRIPTION, CREATED_DATE
) VALUES
      (1, 3, NULL, -30, '미로그인 소멸', NOW()),  -- 회원 1, 소멸된 포인트
      (2, 3, NULL, -50, '장기간 미사용으로 소멸', NOW()),  -- 회원 2, 소멸된 포인트
      (3, 3, NULL, -20, '미로그인 1년 소멸', NOW());  -- 회원 3, 소멸된 포인트

-- ==================================================
-- BOARD
-- 의존성
-- MEMBER
-- ==================================================

-- 게시판 데이터 삽입
INSERT INTO BOARD (
    ID, PARENT_ID, CATEGORY_ID, WRITER_ID, TITLE, CONTENT, FIXED, REG_DATE, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE
) VALUES
      (1, NULL, 1, 5, '배송 지연 안내입니다.', '과도한 물량으로 인해 배송이 지연될 예정입니다.', TRUE, NOW(), NOW(), NOW(), NULL, NULL),
      (2, NULL, 2, 5, '용량 변경 안내입니다.', '녹차 상품의 용량이 변경될 예정입니다.', FALSE, NOW(), NOW(), NOW(), NULL, NULL),
      (3, NULL, 3, 5, 'Q. 사용 가능한 결제 수단에는 무엇이 있나요?', 'A. 현금결제, 카드결제가 가능합니다.', FALSE, NOW(), NOW(), NOW(), NULL, NULL),
      (4, NULL, 4, 5, 'Q. 환불은 언제까지 가능한가요?', 'A. 주문이 완료된 시점부터 14일 이내에 가능합니다.', FALSE, NOW(), NOW(), NOW(), NULL, NULL),
      (5, NULL, 5, 5, 'Q. 배송이 될때까지 얼마나 걸리나요?', 'A. 보통 영업일 2~4일 이내에 배송이 완료될 예정입니다.', FALSE, NOW(), NOW(), NOW(), NULL, NULL),
      (6, NULL, 6, 5, 'Q. 포인트는 얼마부터 사용 가능한가요?', 'A. 금액에 상관없이 사용가능하십니다.', FALSE, NOW(), NOW(), NOW(), NULL, NULL),
      (7, NULL, 7, 5, 'Q. 탈퇴 후 며칠이내에 재가입이 가능한가요?', 'A. 저희 사이트는 같은 이메일로 재가입이 불가능합니다. 다른 이메일로 가입하여 주시길 바랍니다.', FALSE, NOW(), NOW(), NOW(), NULL, NULL),
      (8, NULL, 8, 2, 'Q. 환불 문의입니다.', '어제 구입한 상품 환불해주세요.', FALSE, NOW(), NOW(), NOW(), NULL, NULL);

-- ==================================================
-- BOARD_IMAGE
-- 의존성
-- BOARD
-- ==================================================

-- 게시판 이미지 데이터 삽입
INSERT INTO BOARD_IMAGE (
    ID, BOARD_ID, IMAGE, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE
) VALUES
      (1, 1, ' ', NOW(), NOW(), NULL, NULL),
      (2, 1, ' ', NOW(), NOW(), NULL, NULL),
      (3, 1, ' ', NOW(), NOW(), NULL, NULL),
      (4, 1, ' ', NOW(), NOW(), NULL, NULL),
      (5, 2, ' ', NOW(), NOW(), NULL, NULL),
      (6, 3, ' ', NOW(), NOW(), NULL, NULL),
      (7, 3, ' ', NOW(), NOW(), NULL, NULL),
      (8, 4, ' ', NOW(), NOW(), NULL, NULL);
