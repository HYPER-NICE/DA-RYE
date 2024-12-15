-- =========================================
-- 카테고리
-- =========================================

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

-- =========================================
-- 상품
-- 카테고리에 의존성이 있다.
-- =========================================

-- 상품 데이터 삽입
INSERT INTO PRODUCT (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION, MAIN_DESCRIPTION, PRICE,
                     CATEGORY_ID, IMPORTER, MANUFACTURER, EXPIRATION_DATE, SIZE, CAPACITY,
                     INGREDIENTS, PRECAUTIONS, SALE_DATE,REGISTRATION_DATE, NEW, HOT, BEST,
                     COMMON_CODE_ID)
VALUES ('물에 잘녹는 녹차', '순수한 녹차 잎', '전통 방식으로 가공한 녹차로, 신선하고 건강한 맛을 제공합니다.', '고급스러운 맛의 순수 녹차, 차 한 잔으로 자연을 느껴보세요.', 5000,
        1, '한국차유통', '차나라', '2025-12-31 00:00:00', '중', '100g', '녹차', '직사광선을 피해서 보관하세요.', '2024-01-01 00:00:00',
        '2024-01-01 00:00:00',TRUE, FALSE, TRUE, 1),

       ('물에 잘녹는 홍차', '진한 향과 맛의 홍차', '영국식 전통 홍차로, 우려내면 깊고 진한 향이 퍼집니다.', '진한 홍차의 향기를 즐기세요. 영국식 다도에 적합한 홍차입니다.', 6000,
        1, '영국차수입', '홍차명가', '2026-06-30 00:00:00', '대', '150g', '홍차', '뜨거운 물에 3-5분 우려서 드세요.', '2024-02-01 00:00:00',
        '2024-02-01 00:00:00',FALSE, TRUE, FALSE, 2),

       ('물에 잘녹는 루이보스 퓨어 차', '다양한 허브가 어우러진 루이보스차', '허브와 꽃을 혼합하여 만든 건강에 좋은 허브차입니다.', '스트레스를 풀어주는 허브차, 잠자리 전 마시기 좋습니다.', 7000,
        1, '허브유통', '허브가든', '2025-06-30 00:00:00', '소', '50g', '카모마일, 라벤더, 민트', '알레르기 반응을 확인 후 섭취하세요.',
        '2024-03-01 00:00:00', '2024-03-01 00:00:00',TRUE, FALSE, FALSE, 1),

       ('물에 잘녹는 유기농 녹차', '유기농으로 재배한 녹차', '자연에서 기른 유기농 녹차, 인공 첨가물이 없습니다.', '100% 유기농으로 재배된 녹차로 건강한 맛을 제공합니다.', 8000,
        1, '유기농차회사', '그린티코퍼레이션', '2025-12-31 00:00:00', '중', '200g', '녹차', '서늘하고 건조한 곳에 보관하세요.',
        '2024-04-01 00:00:00', '2024-04-01 00:00:00',FALSE, TRUE, FALSE, 1),

       ('물에 잘녹는 얼그레이', '향긋한 얼그레이 차', '유럽에서 전통적인 방식으로 만든 얼그레이 차입니다.', '얼그레이 특유의 은은한 향을 느끼실 수 있습니다.', 7500,
        1, '프랑스차수입', '벨로르티', '2026-01-01 00:00:00', '소', '75g', '홍차, 베르가못', '직사광선을 피하고 서늘한 곳에 보관하세요.',
        '2024-05-01 00:00:00', '2024-05-01 00:00:00',FALSE, FALSE, TRUE, 3);
