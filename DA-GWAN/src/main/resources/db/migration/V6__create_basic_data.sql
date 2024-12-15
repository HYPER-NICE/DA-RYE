-- =========================================
-- 기본 데이터 삽입
-- =========================================

-- 멤버 등급 삽입
INSERT INTO MEMBER_GRADE_POLICY (GRADE_NAME, MIN_AMOUNT, MAX_AMOUNT, PERIOD_DAYS, DESCRIPTION)
VALUES ('나누리', 100000, 999999999, 360, '최근 360일 동안 10만원 이상 구매 고객'),
       ('벗드리', 50000, 99999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객'),
       ('다소니', 10000, 49999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객'),
       ('마루한', 0, 9999, 90, '최근 90일 동안 1만원 미만 구매 고객');

INSERT INTO MEMBER_GRADE_POLICY_HISTORY (GRADE_ID, GRADE_NAME, MIN_AMOUNT, MAX_AMOUNT, PERIOD_DAYS, DESCRIPTION, OPERATION_TYPE, HISTORY_CREATED_DATE)
VALUES
    (1, '나누리', 200000, 999999999, 360, '최근 360일 동안 10만원 이상 구매 고객', 'UPDATE', CURRENT_TIMESTAMP),
    (2, '벗드리', 100000, 19999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객', 'UPDATE', CURRENT_TIMESTAMP),
    (3, '다소니', 30000, 99999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객', 'UPDATE', CURRENT_TIMESTAMP),
    (4, '마루한', 0, 29999, 90, '최근 90일 동안 1만원 미만 구매 고객', 'UPDATE', CURRENT_TIMESTAMP);

-- 멤버 삽입
INSERT INTO MEMBER (ID, NAME, SEX, BIRTHDATE, EMAIL, PASSWORD, ADDRESS, ADDRESS_LINE, ADDRESS_ZIP_CODE, MOBILE, LANDLINE, GRADE_ID, CURRENT_POINTS, TOTAL_EARNED_POINTS, TOTAL_REDEEMED_POINTS, MEMBER_STATUS, LATEST_LOGIN_DATE, REG_DATE)
VALUES
    (1, '홍길동', 'M', '1985-05-15', 'hong.gildong@example.com', 'password123', '서울특별시 강남구 테헤란로 123', '빌딩 5층 501호', '06123', '010-1234-5678', '02-555-1234', 4, 500, 1000, 200, 'Active', '2024-12-01 10:30:00', '2024-01-15 08:00:00'),
    (2, '김영희', 'F', '1992-08-20', 'kim.younghee@example.com', 'password456', '서울특별시 송파구 올림픽로 456', '세븐타워 14층', '05567', '010-2345-6789', '02-555-5678', 2, 300, 700, 100, 'Active', '2024-12-01 12:45:00', '2024-02-18 09:30:00'),
    (3, '이수진', 'F', '1988-11-10', 'lee.sujin@example.com', 'password789', '부산광역시 해운대구 센텀동로 789', '그랜드맨션 3층 303호', '48003', '010-3456-7890', '051-555-2345', 3, 600, 1200, 400, 'Active', '2024-12-01 14:00:00', '2024-03-20 10:15:00'),
    (4, '박철수', 'M', '1975-02-25', 'park.chulsoo@example.com', 'password101', '경기도 수원시 영통구 광교로 101', '아파트 201호', '16644', '010-4567-8901', '031-555-3456', 4, 100, 200, 50, 'Inactive', '2024-12-01 15:30:00', '2024-04-25 11:00:00'),
    (5, '정민수', 'M', '2000-06-10', 'jeong.minsu@example.com', 'password102', '대구광역시 중구 동성로 202', '상가 4층', '41922', '010-5678-9012', '053-555-6789', 1, 800, 1500, 500, 'Active', '2024-12-01 16:00:00', '2024-05-30 12:00:00');

-- 배송지 주소 삽입
INSERT INTO DELIVERY_ADDRESS (MEMBER_ID, DELIVERY_ADDRESS, DELIVERY_ADDRESS_LINE, DELIVERY_ADDRESS_ZIP_CODE, ADDRESS_TYPE, IS_PRIMARY_ADDRESS, IS_LATEST_ADDRESS)
VALUES
    (1, '서울특별시 강남구 테헤란로 123', '빌딩 5층 501호', 06123, '자택', TRUE, TRUE),
    (1, '서울특별시 강남구 역삼로 456', '빌딩 2층 202호', 06124, '직장', FALSE, FALSE),
    (2, '서울특별시 송파구 올림픽로 456', '세븐타워 14층', 05567, '자택', TRUE, TRUE),
    (2, '서울특별시 송파구 문정로 123', '건물 7층 703호', 05568, '직장', FALSE, FALSE),
    (3, '부산광역시 해운대구 센텀동로 789', '그랜드맨션 3층 303호', 48003, '자택', TRUE, TRUE);

-- 멤버 이력 삽입
INSERT INTO MEMBER_HISTORY (MEMBER_ID, NAME, EMAIL, PASSWORD, ADDRESS, MOBILE, GRADE_ID, OPERATION_TYPE, HISTORY_CREATED_DATE)
VALUES
    (1, '홍길동', 'hong.gildong@example.com', 'password123', '서울특별시 강남구 테헤란로 123', '010-1234-5678', 1, 'UPDATE', '2024-01-15 08:00:00'),
    (1, '홍길동', 'hong.gildong@example.com', 'newpassword123', '서울특별시 강남구 테헤란로 123', '010-1234-5678', 1, 'UPDATE', '2024-02-01 09:00:00'),
    (2, '김영희', 'kim.younghee@example.com', 'password456', '서울특별시 송파구 올림픽로 456', '010-2345-6789', 2, 'UPDATE', '2024-02-18 09:30:00'),
    (3, '이수진', 'lee.sujin@example.com', 'password789', '부산광역시 해운대구 센텀동로 789', '010-3456-7890', 3, 'DELETE', '2024-03-20 10:15:00'),
    (4, '박철수', 'park.chulsoo@example.com', 'password101', '경기도 수원시 영통구 광교로 101', '010-4567-8901', 4, 'DELETE', '2024-04-25 11:00:00');



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

# -- 상품 상태 코드 기본 데이터 삽입
# INSERT INTO PRODUCT_STATUS_CODE (NAME, DESCRIPTION)
# VALUES ('ON_SALE', '판매중'),
#        ('SOLD OUT', '품절'),
#        ('DISCONTINUED', '단종');
#
#
#
#
# -- 입고 코드 기본 데이터 삽입
# INSERT INTO INBOUND_CODE (NAME, DESCRIPTION)
# VALUES ('PURCHASE', '공급업체로부터 신규 구매 입고'),
#        ('CUSTOMER_RETURN', '고객 반품 상품 재입고'),
#        ('PROMOTIONAL_EVENT', '프로모션 목적 재고 확보 입고');
#
# -- 출고 코드 기본 데이터 삽입
# INSERT INTO OUTBOUND_CODE (NAME, DESCRIPTION)
# VALUES ('DAMAGED', '상품 파손 폐기 출고'),
#        ('DONATION', '기부 목적 출고'),
#        ('TRANSFER_OUT', '외부 창고 이동 출고');


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

# -- 고정 포인트 정책 데이터 삽입
# INSERT INTO FIXED_POINT_POLICY (POLICY_NAME, DESCRIPTION)
# VALUES ('NEW_SIGNUP', '신규 회원 가입 포인트 지급'),
#        ('BIRTHDAY', '생일 포인트 지급');
#
# -- 고정 포인트 금액 데이터 삽입
# INSERT INTO FIXED_POINT_POLICY_DETAIL (FIXED_POLICY_ID, POINT_AMOUNT, EFFECTIVE_START_DATE, EFFECTIVE_END_DATE)
# SELECT ID,
#        CASE POLICY_NAME
#            WHEN 'NEW_SIGNUP' THEN 5000
#            WHEN 'BIRTHDAY' THEN 2000
#            END,
#        NOW(),
#        NULL
# FROM FIXED_POINT_POLICY;
#
# -- 퍼센트 포인트 정책 데이터 삽입
# INSERT INTO PERCENTAGE_POINT_POLICY (MEMBER_GRADE_POLICY_ID)
# SELECT ID
# FROM MEMBER_GRADE_POLICY;
#
# -- 퍼센트 포인트 금액 삽입
# INSERT INTO PERCENTAGE_POINT_POLICY_DETAIL (PERCENTAGE_POLICY_ID, PERCENTAGE, EFFECTIVE_START_DATE)
# SELECT ID,
#        CASE GRADE_NAME
#            WHEN '마루한' THEN 2.00
#            WHEN '다소니' THEN 3.00
#            WHEN '벗드리' THEN 5.00
#            WHEN '나누리' THEN 7.00
#            END,
#        NOW()
# FROM MEMBER_GRADE_POLICY;

-- 퍼센트 기반 포인트 정책 삽입
INSERT INTO PERCENTAGE_POINT_POLICY (MEMBER_GRADE_POLICY_ID, DESCRIPTION, ACTIVATED)
VALUES
    (1, '나누리 등급 고객의 포인트 적립 정책', TRUE),
    (2, '벗드리 등급 고객의 포인트 적립 정책', TRUE),
    (3, '다소니 고객의 포인트 적립 정책', TRUE),
    (4, '마루한 등급 고객의 포인트 적립 정책', TRUE);

-- 퍼센트 기반 포인트 정책 상세
INSERT INTO PERCENTAGE_POINT_POLICY_DETAIL (PERCENTAGE_POLICY_ID, PERCENTAGE, EFFECTIVE_START_DATE, EFFECTIVE_END_DATE)
VALUES
    (1, 5.00, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (2, 3.00, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (3, 2.00, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (4, 1.00, '2024-01-01 00:00:00', '2024-12-31 23:59:59');

-- 고정 포인트 정책 삽입
INSERT INTO FIXED_POINT_POLICY (POLICY_NAME, DESCRIPTION, ACTIVATED)
VALUES
    ('가입 축하 포인트', '회원 가입을 축하하는 고정 포인트 지급 정책', TRUE),
    ('고객 생일 포인트', '고객의 생일을 기념하여 지급하는 고정 포인트 정책', TRUE),
    ('친구 추천 포인트', '친구를 추천한 고객에게 지급하는 고정 포인트 정책', TRUE),
    ('휴면 고객 재활성화 포인트', '휴면 고객의 재활성화를 위한 고정 포인트 정책', TRUE),
    ('첫 구매 포인트', '첫 구매 시 지급하는 고정 포인트 정책', TRUE);

-- FIXED_POINT_POLICY_DETAIL 테이블에 더미 데이터 삽입 (각 고정 포인트 정책의 지급 금액)
INSERT INTO FIXED_POINT_POLICY_DETAIL (FIXED_POLICY_ID, POINT_AMOUNT, EFFECTIVE_START_DATE, EFFECTIVE_END_DATE)
VALUES
    (1, 1000, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (2, 3000, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (3, 2000, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (4, 500, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (5, 1000, '2024-01-01 00:00:00', '2024-12-31 23:59:59');

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

-- 공통 코드 삽입
INSERT INTO COMMON_CODE (code, name, description)
VALUES ('P0301', '입고 상태(입고 완료)', '입고가 완료되어 입고 로케이션에 배치해둔 상태'),
       ('P0302', '입고 상태(반품 수거)', '반품으로 인해 다시 입고가 되었음을 나타내는 상태'),
       ('P0303', '입고 상태(교환 수거)', '교환으로 인해 다시 입고가 되었음을 나타내는 상태');

-- 상품 데이터 삽입
INSERT INTO PRODUCT (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION, MAIN_DESCRIPTION, PRICE,
                     CATEGORY_ID, IMPORTER, MANUFACTURER, EXPIRATION_DATE, SIZE, CAPACITY,
                     INGREDIENTS, PRECAUTIONS, SALE_DATE,REGISTRATION_DATE, NEW, HOT, BEST,
                     COMMON_CODE_ID)
VALUES ('녹차', '순수한 녹차 잎', '전통 방식으로 가공한 녹차로, 신선하고 건강한 맛을 제공합니다.', '고급스러운 맛의 순수 녹차, 차 한 잔으로 자연을 느껴보세요.', 5000,
        27, '한국차유통', '차나라', '2025-12-31 00:00:00', '중', '100g', '녹차', '직사광선을 피해서 보관하세요.', '2024-01-01 00:00:00',
        '2024-01-01 00:00:00',TRUE, FALSE, TRUE, 1),

       ('홍차', '진한 향과 맛의 홍차', '영국식 전통 홍차로, 우려내면 깊고 진한 향이 퍼집니다.', '진한 홍차의 향기를 즐기세요. 영국식 다도에 적합한 홍차입니다.', 6000,
        28, '영국차수입', '홍차명가', '2026-06-30 00:00:00', '대', '150g', '홍차', '뜨거운 물에 3-5분 우려서 드세요.', '2024-02-01 00:00:00',
        '2024-02-01 00:00:00',FALSE, TRUE, FALSE, 2),

       ('루이보스 퓨어 차', '다양한 허브가 어우러진 루이보스차', '허브와 꽃을 혼합하여 만든 건강에 좋은 허브차입니다.', '스트레스를 풀어주는 허브차, 잠자리 전 마시기 좋습니다.', 7000,
        29, '허브유통', '허브가든', '2025-06-30 00:00:00', '소', '50g', '카모마일, 라벤더, 민트', '알레르기 반응을 확인 후 섭취하세요.',
        '2024-03-01 00:00:00', '2024-03-01 00:00:00',TRUE, FALSE, FALSE, 1),

       ('유기농 녹차', '유기농으로 재배한 녹차', '자연에서 기른 유기농 녹차, 인공 첨가물이 없습니다.', '100% 유기농으로 재배된 녹차로 건강한 맛을 제공합니다.', 8000,
        30, '유기농차회사', '그린티코퍼레이션', '2025-12-31 00:00:00', '중', '200g', '녹차', '서늘하고 건조한 곳에 보관하세요.',
        '2024-04-01 00:00:00', '2024-04-01 00:00:00',FALSE, TRUE, FALSE, 1),

       ('얼그레이', '향긋한 얼그레이 차', '유럽에서 전통적인 방식으로 만든 얼그레이 차입니다.', '얼그레이 특유의 은은한 향을 느끼실 수 있습니다.', 7500,
        31, '프랑스차수입', '벨로르티', '2026-01-01 00:00:00', '소', '75g', '홍차, 베르가못', '직사광선을 피하고 서늘한 곳에 보관하세요.',
        '2024-05-01 00:00:00', '2024-05-01 00:00:00',FALSE, FALSE, TRUE, 3);

-- 장바구니 삽입
INSERT INTO CART (MEMBER_ID, TOTAL_PRICE, TOTAL_DISCOUNT, TOTAL_EARNING_POINTS, REDEEMED_POINTS, CREATED_DATE, LAST_MODIFIED_DATE)
VALUES
    (1, 10000, 500, 200, 100, '2024-12-01 10:30:00', '2024-12-01 10:30:00'),
    (2, 15000, 800, 300, 200, '2024-12-01 11:00:00', '2024-12-01 11:00:00'),
    (3, 12000, 600, 250, 150, '2024-12-01 12:00:00', '2024-12-01 12:00:00'),
    (4, 5000, 200, 100, 50, '2024-12-01 13:00:00', '2024-12-01 13:00:00'),
    (5, 13000, 700, 350, 250, '2024-12-01 14:00:00', '2024-12-01 14:00:00');

--
INSERT INTO CART_ITEM (CART_ID, PRODUCT_ID, ORDER_QUANTITY, TOTAL_PRODUCT_PRICE, TOTAL_PRODUCT_DISCOUNT, PRODUCT_EARNING_POINTS)
VALUES
    (1, 1, 2, 10000, 500, 200),
    (1, 3, 1, 7000, 300, 100),
    (2, 2, 3, 18000, 800, 300),
    (2, 4, 1, 8000, 400, 150),
    (3, 5, 2, 15000, 600, 250);

-- 주문 메인 삽입
INSERT INTO ORDER_MAIN (MEMBER_ID, ORDER_DATE)
VALUES
    (1, '2024-12-01 10:35:00'),
    (2, '2024-12-02 11:15:00'),
    (3, '2024-12-03 14:45:00'),
    (4, '2024-12-04 16:20:00'),
    (5, '2024-12-05 09:10:00');

-- 주문 상세 삽입
INSERT INTO ORDER_DETAIL (ORDER_ID, PRODUCT_ID, AMOUNT)
VALUES
    (1, 1, 2),
    (1, 2, 1),
    (2, 3, 3),
    (3, 4, 1),
    (4, 5, 4);

-- 주문 상세 상태 변경 이력 삽입
INSERT INTO ORDER_DETAIL_STATUS (ORDER_DETAIL_ID, ORDER_STATUS_CODE_ID, ORDER_DATE)
VALUES
    (1, 1, '2024-12-01 10:45:00'),
    (2, 2, '2024-12-01 11:00:00'),
    (3, 3, '2024-12-02 13:30:00'),
    (4, 4, '2024-12-03 14:00:00'),
    (5, 5, '2024-12-04 15:10:00');

-- 주문 배송 메인 삽입
INSERT INTO ORDER_DELIVERY_MAIN (ORDER_MAIN_ID, DELIVERY_COMPANY_NAME, TRACKING_NUMBER, DELIVERY_REQUEST_NOTE)
VALUES
    (1, 'CJ대한통운', '1234567890', '빠른 배송 요청'),
    (2, '한진택배', '9876543210', '토요일 배송 희망'),
    (3, '로젠택배', '1122334455', '집앞에 놓아주세요'),
    (4, '우체국택배', '9988776655', '직접 수령'),
    (5, '쿠팡로지스틱스', '5566778899', '사무실로 배송');

-- 주문 배송 상태 변경 이력 삽입
INSERT INTO ORDER_DELIVERY_STATUS (ORDER_DELIVERY_MAIN_ID, DELIVERY_STATUS_CODE_ID, DELIVERY_DATE)
VALUES
    (1, 1, '2024-12-01 11:00:00'),
    (2, 2, '2024-12-02 12:30:00'),
    (3, 3, '2024-12-03 13:45:00'),
    (4, 4, '2024-12-04 14:20:00'),
    (5, 5, '2024-12-05 15:10:00');

-- 주문 결제 메인 더미 데이터
INSERT INTO ORDER_PAYMENT_MAIN (ORDER_ID, TOTAL_AMOUNT)
VALUES
    (1, 50000),
    (2, 30000),
    (3, 45000),
    (4, 20000),
    (5, 60000);

-- 주문 결제 상세 더미 데이터
INSERT INTO ORDER_PAYMENT_DETAIL (ORDER_PAYMENT_MAIN_ID, PAYMENT_METHOD_CODE_ID, ORDER_PAYMENT_STATUS_CODE_ID, PAYMENT_AMOUNT, PAYMENT_DATE, COMMENT)
VALUES
    (1, 1, 1, 50000, '2024-12-01 12:00:00', '신용카드 결제'),
    (2, 2, 2, 30000, '2024-12-02 14:00:00', '계좌이체 결제'),
    (3, 1, 3, 45000, '2024-12-03 15:30:00', '신용카드 결제, 할부 3개월'),
    (4, 3, 1, 20000, '2024-12-04 16:45:00', '휴대폰 결제'),
    (5, 2, 2, 60000, '2024-12-05 17:00:00', '계좌이체 결제');