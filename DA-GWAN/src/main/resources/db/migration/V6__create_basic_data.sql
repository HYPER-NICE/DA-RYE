-- =========================================
-- 기본 데이터 삽입
-- =========================================

-- 멤버 등급 기본 데이터 삽입
INSERT INTO MEMBER_GRADE_POLICY (GRADE_NAME, MIN_AMOUNT, MAX_AMOUNT, PERIOD_DAYS, DESCRIPTION)
VALUES ('나누리', 100000, 999999999, 360, '최근 360일 동안 10만원 이상 구매 고객'),
       ('벗드리', 50000, 99999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객'),
       ('다소니', 10000, 49999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객'),
       ('마루한', 0, 9999, 90, '최근 90일 동안 1만원 미만 구매 고객');

-- 루트 카테고리 삽입
INSERT INTO CATEGORY (NAME, PARENT_ID, SCREEN_ORDER)
VALUES ('차', NULL, 1),
       ('차기', NULL, 2);

-- 2뎁스 카테고리 삽입
INSERT INTO CATEGORY (NAME, PARENT_ID, SCREEN_ORDER)
SELECT NAME, ID, SCREEN_ORDER
FROM (SELECT '녹차' AS NAME, ID, 1 AS SCREEN_ORDER
      FROM CATEGORY
      WHERE NAME = '차'
      UNION
      SELECT '홍차' AS NAME, ID, 2 AS SCREEN_ORDER
      FROM CATEGORY
      WHERE NAME = '차'
      UNION
      SELECT '다기 세트' AS NAME, ID, 1 AS SCREEN_ORDER
      FROM CATEGORY
      WHERE NAME = '차기'
      UNION
      SELECT '찻잔' AS NAME, ID, 2 AS SCREEN_ORDER
      FROM CATEGORY
      WHERE NAME = '차기'
      UNION
      SELECT '허브차' AS NAME, ID, 3 AS SCREEN_ORDER
      FROM CATEGORY
      WHERE NAME = '차') AS SUB_CATEGORIES;

-- 3뎁스 카테고리 삽입
INSERT INTO CATEGORY (NAME, PARENT_ID, SCREEN_ORDER)
SELECT NAME, ID, SCREEN_ORDER
FROM (
         -- 차 - 녹차 하위
         SELECT '유기농 녹차' AS NAME, ID, 1 AS SCREEN_ORDER
         FROM CATEGORY
         WHERE NAME = '녹차'
         UNION
         SELECT '보성 녹차' AS NAME, ID, 2
         FROM CATEGORY
         WHERE NAME = '녹차'

         -- 차 - 홍차 하위
         UNION
         SELECT '다즐링 홍차' AS NAME, ID, 1
         FROM CATEGORY
         WHERE NAME = '홍차'
         UNION
         SELECT '아쌈 홍차' AS NAME, ID, 2
         FROM CATEGORY
         WHERE NAME = '홍차'

         -- 차 - 허브차 하위
         UNION
         SELECT '루이보스 차' AS NAME, ID, 1
         FROM CATEGORY
         WHERE NAME = '허브차'
         UNION
         SELECT '히비스커스 차' AS NAME, ID, 2
         FROM CATEGORY
         WHERE NAME = '허브차'

         -- 차기 - 다기 세트 하위
         UNION
         SELECT '전통 다기 세트' AS NAME, ID, 1
         FROM CATEGORY
         WHERE NAME = '다기 세트'
         UNION
         SELECT '현대식 다기 세트' AS NAME, ID, 2
         FROM CATEGORY
         WHERE NAME = '다기 세트'

         -- 차기 - 찻잔 하위
         UNION
         SELECT '도자기 찻잔' AS NAME, ID, 1
         FROM CATEGORY
         WHERE NAME = '찻잔'
         UNION
         SELECT '유리 찻잔' AS NAME, ID, 2
         FROM CATEGORY
         WHERE NAME = '찻잔') AS SUB_CATEGORIES;

-- 상품 상태 코드 기본 데이터 삽입
INSERT INTO PRODUCT_STATUS_CODE (NAME, DESCRIPTION)
VALUES ('ON_SALE', '판매중'),
       ('SOLD OUT', '품절'),
       ('DISCONTINUED', '단종');


-- 입고 코드 기본 데이터 삽입
INSERT INTO INBOUND_CODE (NAME, DESCRIPTION)
VALUES ('PURCHASE', '공급업체로부터 신규 구매 입고'),
       ('CUSTOMER_RETURN', '고객 반품 상품 재입고'),
       ('PROMOTIONAL_EVENT', '프로모션 목적 재고 확보 입고');

-- 출고 코드 기본 데이터 삽입
INSERT INTO OUTBOUND_CODE (NAME, DESCRIPTION)
VALUES ('DAMAGED', '상품 파손 폐기 출고'),
       ('DONATION', '기부 목적 출고'),
       ('TRANSFER_OUT', '외부 창고 이동 출고');


-- 주문 상태 기본 데이터 삽입
INSERT INTO ORDER_STATUS_CODE (NAME, DESCRIPTION)
VALUES ('PAYMENT_PENDING', '결제 대기'),
       ('PAYMENT_COMPLETED', '결제 완료'),
       ('CANCELLATION_REQUESTED', '취소 요청 대기'),
       ('CANCELLATION_COMPLETED', '취소 완료'),
       ('PARTIAL_CANCELLATION_COMPLETED', '주문 일부 상품만 취소 완료'),
       ('FULL_CANCELLATION_COMPLETED', '주문 전체 취소 완료');

-- 배송 상태 기본 데이터 삽입
INSERT INTO DELIVERY_STATUS_CODE (NAME, DESCRIPTION)
VALUES ('PREPARING_SHIPMENT', '배송 준비 중'),
       ('SHIPPED', '배송 중'),
       ('DELIVERED', '배송 완료'),
       ('RETURN_REQUESTED', '반품 요청 대기'),
       ('RETURN_COMPLETED', '반품 완료'),
       ('EXCHANGE_REQUESTED', '교환 요청 대기'),
       ('EXCHANGE_COMPLETED', '교환 완료');

-- 결제 코드 기본 데이터 삽입
INSERT INTO PAYMENT_METHOD_CODE (NAME, DESCRIPTION)
VALUES ('POINT', '포인트 결제'),
       ('BANK_TRANSFER', '계좌 이체 결제'),
       ('CANCEL', '결제 취소');
-- 포인트 결제, 카드 결제, 취소 예정, 취소 완료, 이제 예정, 결제 예정, 카드결제 완료

-- 결제 상태 코드 초기 데이터 삽입
INSERT INTO PAYMENT_STATUS_CODE (NAME, DESCRIPTION)
VALUES ('PENDING', '결제가 대기 상태입니다.'),
       ('COMPLETED', '결제가 성공적으로 완료되었습니다.'),
       ('FAILED', '결제가 실패했습니다.'),
       ('CANCELED', '결제가 취소되었습니다.'),
       ('REFUNDED', '결제가 환불되었습니다.');

-- 포인트 거래 코드 기본 데이터 삽입
INSERT INTO POINT_TRANSACTION_TYPE (NAME, DESCRIPTION)
VALUES ('PENDING', '포인트 적립/사용 대기'),
       ('COMPLETED', '포인트 적립/사용 완료'),
       ('CANCELLED', '포인트 적립/사용 취소');

-- 고정 포인트 정책 데이터 삽입
INSERT INTO FIXED_POINT_POLICY (POLICY_NAME, DESCRIPTION)
VALUES ('NEW_SIGNUP', '신규 회원 가입 포인트 지급'),
       ('BIRTHDAY', '생일 포인트 지급');

-- 고정 포인트 금액 데이터 삽입
INSERT INTO FIXED_POINT_AMOUNT (FIXED_POLICY_ID, POINT_AMOUNT, EFFECTIVE_START_DATE, EFFECTIVE_END_DATE)
SELECT ID,
       CASE POLICY_NAME
           WHEN 'NEW_SIGNUP' THEN 5000
           WHEN 'BIRTHDAY' THEN 2000
           END,
       NOW(),
       NULL
FROM FIXED_POINT_POLICY;

-- 퍼센트 포인트 정책 데이터 삽입
INSERT INTO PERCENTAGE_POINT_POLICY (MEMBER_GRADE_POLICY_ID)
SELECT ID
FROM MEMBER_GRADE_POLICY;

-- 퍼센트 포인트 금액 삽입
INSERT INTO PERCENTAGE_POINT_AMOUNT (PERCENTAGE_POLICY_ID, PERCENTAGE, EFFECTIVE_START_DATE)
SELECT ID,
       CASE GRADE_NAME
           WHEN '마루한' THEN 1.00
           WHEN '다소니' THEN 2.00
           WHEN '벗드리' THEN 3.00
           WHEN '나누리' THEN 4.00
           END,
       NOW()
FROM MEMBER_GRADE_POLICY;

-- 약관 메인 데이터 삽입
INSERT INTO TERMS (NAME, DESCRIPTION)
VALUES ('이용약관', '서비스 이용에 관한 일반 약관'),
       ('개인정보처리방침', '개인정보의 수집, 이용, 처리 및 보호에 관한 약관');

-- 약관 버전 데이터 삽입
INSERT INTO TERMS_VERSION (TERMS_ID, VERSION_NO, CONTENT, EFFECTIVE_START_DATE)
SELECT ID,
       1,
       '이 약관은 회사의 서비스 사용과 관련된 기본 사항을 규정합니다.',
       NOW()
FROM TERMS;

-- 서브 약관 데이터 삽입
INSERT INTO SUB_TERMS (TERMS_ID, NAME, DESCRIPTION)
SELECT ID,
       '개인정보 이용 동의',
       '서비스 이용 시 개인정보 이용에 대한 동의 조항'
FROM TERMS
WHERE NAME = '개인정보처리방침';

-- 서브 약관 버전 데이터 삽입
INSERT INTO SUB_TERMS_VERSION (SUB_TERMS_ID, VERSION_NO, CONTENT, EFFECTIVE_START_DATE)
SELECT ID,
       1,
       '서비스 제공을 위해 개인정보 이용에 동의합니다.',
       NOW()
FROM SUB_TERMS;

-- 상품 데이터 삽입
INSERT INTO PRODUCT (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION, MAIN_DESCRIPTION, PRICE,
                     CATEGORY_ID, STATUS_ID, IMPORTER, MANUFACTURER, EXPIRATION_DATE,
                     SIZE, CAPACITY, INGREDIENTS, PRECAUTIONS, SALE_DATE, NEW, HOT, BEST)
VALUES ('녹차', '순수한 녹차 잎', '전통 방식으로 가공한 녹차로, 신선하고 건강한 맛을 제공합니다.', '고급스러운 맛의 순수 녹차, 차 한 잔으로 자연을 느껴보세요.', 5000,
        11, 1, '한국차유통', '차나라', '2025-12-31 00:00:00', '중', '100g', '녹차', '직사광선을 피해서 보관하세요.', '2024-01-01 00:00:00',
        TRUE, FALSE, TRUE),

       ('홍차', '진한 향과 맛의 홍차', '영국식 전통 홍차로, 우려내면 깊고 진한 향이 퍼집니다.', '진한 홍차의 향기를 즐기세요. 영국식 다도에 적합한 홍차입니다.', 6000,
        12, 2, '영국차수입', '홍차명가', '2026-06-30 00:00:00', '대', '150g', '홍차', '뜨거운 물에 3-5분 우려서 드세요.', '2024-02-01 00:00:00',
        FALSE, TRUE, FALSE),

       ('루이보스 퓨어 차', '다양한 허브가 어우러진 루이보스차', '허브와 꽃을 혼합하여 만든 건강에 좋은 허브차입니다.', '스트레스를 풀어주는 허브차, 잠자리 전 마시기 좋습니다.', 7000,
        14, 1, '허브유통', '허브가든', '2025-06-30 00:00:00', '소', '50g', '카모마일, 라벤더, 민트', '알레르기 반응을 확인 후 섭취하세요.',
        '2024-03-01 00:00:00', TRUE, FALSE, FALSE),

       ('유기농 녹차', '유기농으로 재배한 녹차', '자연에서 기른 유기농 녹차, 인공 첨가물이 없습니다.', '100% 유기농으로 재배된 녹차로 건강한 맛을 제공합니다.', 8000,
        10, 1, '유기농차회사', '그린티코퍼레이션', '2025-12-31 00:00:00', '중', '200g', '녹차', '서늘하고 건조한 곳에 보관하세요.',
        '2024-04-01 00:00:00', FALSE, TRUE, FALSE),

       ('얼그레이', '향긋한 얼그레이 차', '유럽에서 전통적인 방식으로 만든 얼그레이 차입니다.', '얼그레이 특유의 은은한 향을 느끼실 수 있습니다.', 7500,
        13, 3, '프랑스차수입', '벨로르티', '2026-01-01 00:00:00', '소', '75g', '홍차, 베르가못', '직사광선을 피하고 서늘한 곳에 보관하세요.',
        '2024-05-01 00:00:00', FALSE, FALSE, TRUE);
