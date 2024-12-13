-- =========================================
-- 기본 데이터 삽입
-- =========================================

-- 멤버 등급 기본 데이터 삽입
INSERT INTO member_grade_policy (grade_name, min_amount, max_amount, period_days, description)
VALUES
    ('나누리', 100000, 999999999, 360, '최근 360일 동안 10만원 이상 구매 고객'),
    ('벗드리', 50000, 99999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객'),
    ('다소니', 10000, 49999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객'),
    ('마루한', 0, 9999, 90, '최근 90일 동안 1만원 미만 구매 고객');

-- 배송지 주소 데이터 삽입
INSERT INTO delivery_address (delivery_address, delivery_address_line, delivery_address_zip_code, address_type, is_primary_address, is_latest_address, created_date, last_modified_date, deleted_date)
VALUES
    ('서울시 강남구 역삼동', '123-45', 12345, '집', TRUE, TRUE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    ('부산시 해운대구 센텀시티', '678-90', 67890, '회사', FALSE, TRUE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    ('대구시 수성구 황금동', '101-21', 10121, '친구집', TRUE, FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    ('서울시 마포구 상암동', '22-12', 2212, '실험실', FALSE, FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    ('경기도 성남시 분당구', '112-33', 11233, '자택', TRUE, TRUE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL);

-- 고객 데이터 삽입
INSERT INTO member (id, name, sex, birthdate, email, password, address, address_line, address_zip_code, delivery_address_id, mobile, landline, grade_id, current_points, total_earned_points, total_redeemed_points, member_status, latest_login_date, created_date, last_modified_date, deleted_date)
VALUES
    (1, '김철수', 'M', '1985-05-15', 'kim@example.com', 'password1', '서울시 강남구 역삼동','123-45', '12345', 1, '010-1234-5678', '02-1234-5678', 4, 1000, 2000, 500, 'ACTIVE', '2024-12-01 12:00:00', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (2, '이영희', 'F', '1990-08-25', 'lee@example.com', 'password2', '부산시 해운대구 센텀시티', '678-90', '67890', 2, '010-2345-6789', '051-234-6789', 4, 1500, 3000, 800, 'INACTIVE', '2024-12-02 14:30:00', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (3, '박민수', 'M', '1988-12-03', 'park@example.com', 'password3', '대구시 수성구 황금동', '101-21', '10121', 3, '010-3456-7890', '053-345-7890', 3, 1200, 2500, 600, 'ACTIVE', '2024-12-03 16:00:00', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (4, '정은지', 'F', '1992-04-10', 'jung@example.com', 'password4', '서울시 마포구 상암동', '22-12', '2212', 4, '010-4567-8901', '02-4567-8901', 2, 1300, 2700, 700, 'ACTIVE', '2024-12-04 09:00:00', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (5, '최상훈', 'M', '1995-01-18', 'choi@example.com', 'password5', '경기도 성남시 분당구', '112-33', '11233', 5, '010-5678-9012', '031-567-9012', 1, 1100, 2200, 600, 'INACTIVE', '2024-12-05 10:30:00', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL);

-- 고객 정보 이력 데이터 삽입
INSERT INTO member_history (member_id, name, email, password, address, mobile, grade_id, created_date, last_modified_date, deleted_date, operation_type, history_created_date)
VALUES
    (1, '김철수', 'kim@example.com', 'password1', '서울시 강남구', '010-1234-5678', 1, '2020-01-01 10:00:00', '2020-01-10 15:00:00', NULL, 'UPDATE', CURRENT_TIMESTAMP(6)),
    (1, '김철수', 'kim_updated@example.com', 'password1', '서울시 강남구', '010-9999-8888', 1, '2024-12-01 12:00:00', '2024-12-01 12:30:00', NULL, 'UPDATE', CURRENT_TIMESTAMP(6)),
    (2, '이영희', 'lee@example.com', 'password2', '부산시 해운대구', '010-2345-6789', 2, '2021-02-01 12:00:00', '2021-02-05 13:00:00', NULL, 'UPDATE', CURRENT_TIMESTAMP(6)),
    (2, '이영희', 'lee@example.com', 'password2', '부산시 해운대구', '010-2345-6789', 2, '2021-02-01 12:00:00', '2021-02-05 13:00:00', '2024-12-02 14:30:00', 'UPDATE', CURRENT_TIMESTAMP(6)),
    (3, '박민수', 'park@example.com', 'password3', '대구시 수성구', '010-3456-7890', 3, '2022-03-01 09:00:00', '2022-03-05 14:00:00', NULL, 'UPDATE', CURRENT_TIMESTAMP(6));

-- 고객 등급 이력 데이터 삽입
INSERT INTO member_grade_policy_history (grade_id, grade_name, min_amount, max_amount, period_days, description, created_date, last_modified_date, deleted_date, operation_type, history_created_date)
VALUES
    (1, '나누리', 100000, 999999990, 360, '최근 360일 동안 10만원 이상 구매 고객 (최대 금액 수정)', '2023-01-01 10:00:00', '2024-12-01 12:00:00', NULL, 'UPDATE', CURRENT_TIMESTAMP(6)),
    (2, '멋드리', 50000, 99999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객 (등급 이름 수정)', '2023-02-01 11:00:00', '2024-12-02 14:00:00', NULL, 'UPDATE', CURRENT_TIMESTAMP(6)),
    (3, '다소니', 10000, 49999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객', '2023-03-01 09:00:00', '2023-03-05 16:00:00', '2024-12-03 10:00:00', 'UPDATE', CURRENT_TIMESTAMP(6));

-- 루트 카테고리 삽입
INSERT INTO category (name, parent_id, screen_order)
VALUES
    ('차', NULL, 1),
    ('차기', NULL, 2);

-- 2뎁스 카테고리 삽입
INSERT INTO category (name, parent_id, screen_order)
SELECT name, id, screen_order
FROM (
         SELECT '녹차' AS name, id, 1 as screen_order FROM category WHERE name = '차'
         UNION
         SELECT '홍차' AS name, id, 2 as screen_order FROM category WHERE name = '차'
         UNION
         SELECT '다기 세트' AS name, id, 1 as screen_order FROM category WHERE name = '차기'
         UNION
         SELECT '찻잔' AS name, id, 2 as screen_order FROM category WHERE name = '차기'
         UNION
         SELECT '허브차' AS name, id, 3 as screen_order FROM category WHERE name = '차'
     ) AS sub_categories;

-- 3뎁스 카테고리 삽입
INSERT INTO category (name, parent_id, screen_order)
SELECT name, id, screen_order
FROM (
         -- 차 - 녹차 하위
         SELECT '유기농 녹차' AS name, id, 1 as screen_order FROM category WHERE name = '녹차'
         UNION
         SELECT '보성 녹차' AS name, id, 2 FROM category WHERE name = '녹차'

         -- 차 - 홍차 하위
         UNION
         SELECT '다즐링 홍차' AS name, id,1 FROM category WHERE name = '홍차'
         UNION
         SELECT '아쌈 홍차' AS name, id, 2 FROM category WHERE name = '홍차'

         -- 차 - 허브차 하위
         UNION
         SELECT '루이보스 차' AS name, id,1 FROM category WHERE name = '허브차'
         UNION
         SELECT '히비스커스 차' AS name, id, 2 FROM category WHERE name = '허브차'

         -- 차기 - 다기 세트 하위
         UNION
         SELECT '전통 다기 세트' AS name, id, 1 FROM category WHERE name = '다기 세트'
         UNION
         SELECT '현대식 다기 세트' AS name, id, 2 FROM category WHERE name = '다기 세트'

         -- 차기 - 찻잔 하위
         UNION
         SELECT '도자기 찻잔' AS name, id, 1 FROM category WHERE name = '찻잔'
         UNION
         SELECT '유리 찻잔' AS name, id, 2 FROM category WHERE name = '찻잔'
     ) AS sub_categories;

-- 상품 상태 코드 기본 데이터 삽입
INSERT INTO product_status_code (name, description)
VALUES
    ('ON_SALE', '판매중'),
    ('SOLD OUT', '품절'),
    ('DISCONTINUED', '단종');


-- 입고 코드 기본 데이터 삽입
INSERT INTO inbound_code (name, description)
VALUES
    ('PURCHASE', '공급업체로부터 신규 구매 입고'),
    ('CUSTOMER_RETURN', '고객 반품 상품 재입고'),
    ('PROMOTIONAL_EVENT', '프로모션 목적 재고 확보 입고');

-- 출고 코드 기본 데이터 삽입
INSERT INTO outbound_code (name, description)
VALUES
    ('DAMAGED', '상품 파손 폐기 출고'),
    ('DONATION', '기부 목적 출고'),
    ('TRANSFER_OUT', '외부 창고 이동 출고');


-- 주문 상태 기본 데이터 삽입
INSERT INTO order_code (name, description)
VALUES
    ('PAYMENT_PENDING', '결제 대기'),
    ('PAYMENT_COMPLETED', '결제 완료'),
    ('CANCELLATION_REQUESTED', '취소 요청 대기'),
    ('CANCELLATION_COMPLETED', '취소 완료'),
    ('PARTIAL_CANCELLATION_COMPLETED', '주문 일부 상품만 취소 완료'),
    ('FULL_CANCELLATION_COMPLETED', '주문 전체 취소 완료');

-- 배송 상태 기본 데이터 삽입
INSERT INTO delivery_code (name, description)
VALUES
    ('PREPARING_SHIPMENT', '배송 준비 중'),
    ('SHIPPED', '배송 중'),
    ('DELIVERED', '배송 완료'),
    ('RETURN_REQUESTED', '반품 요청 대기'),
    ('RETURN_COMPLETED', '반품 완료'),
    ('EXCHANGE_REQUESTED', '교환 요청 대기'),
    ('EXCHANGE_COMPLETED', '교환 완료');

-- 결제 코드 기본 데이터 삽입
INSERT INTO payment_method_code (name, description) VALUES
     ('POINT', '포인트 결제'),
     ('BANK_TRANSFER', '계좌 이체 결제');

-- 결제 상태 코드 초기 데이터 삽입
INSERT INTO payment_status_code (name, description)
VALUES
    ('PENDING', '결제가 대기 상태입니다.'),
    ('COMPLETED', '결제가 성공적으로 완료되었습니다.'),
    ('FAILED', '결제가 실패했습니다.'),
    ('CANCELED', '결제가 취소되었습니다.'),
    ('REFUNDED', '결제가 환불되었습니다.');

-- 상태 코드 예시 데이터
INSERT INTO after_sales_status_code (name, description)
VALUES
    ('REQUESTED', '사후 처리 요청됨'),
    ('REJECTED', '요청 거부됨'),
    ('COMPLETED', '처리 완료됨');


-- 사유 코드 예시 데이터
INSERT INTO after_sales_reason_code (name, description)
VALUES
    ('CHANGE_OF_MIND', '단순 변심'),
    ('PRODUCT_DEFECT', '상품 불량'),
    ('WRONG_ITEM_SENT', '잘못된 상품 배송'),
    ('DAMAGE_IN_TRANSIT', '배송 중 파손'),
    ('OTHER', '기타 사유');

-- 고객 주문 데이터 삽입
INSERT INTO member_order_main (
    member_id, orderer_name, orderer_contact, receiver_name, receiver_contact,
    delivery_address, delivery_detail_address, tracking_number, delivery_request_note,
    total_price, order_date, delivery_code_id, order_code_id, created_date, last_modified_date, deleted_date
) VALUES
      (1, '홍길동', '010-1234-5678', '김철수', '010-9876-5432', '서울시 강남구 역삼동 123-45', '서울시 강남구 역삼동 123-45-101호', '123456789012', '문 앞에 놓아주세요.', 50000, '2024-12-11 10:00:00', 1, 1, '2024-12-11 10:00:00', '2024-12-11 10:00:00', NULL),
      (2, '이영희', '010-2345-6789', '박수현', '010-8765-4321', '부산시 해운대구 우동 22-7', '부산시 해운대구 우동 22-7-202호', '987654321098', '배송 전 전화 부탁드립니다.', 120000, '2024-12-11 11:15:00', 2, 2, '2024-12-11 11:15:00', '2024-12-11 11:15:00', NULL),
      (3, '김민수', '010-3456-7890', '정다은', '010-7654-3210', '대전시 유성구 가정동 1-2', '대전시 유성구 가정동 1-2-301호', '456789123456', '포장 상태 확인 후 수령해주세요.', 75000, '2024-12-11 12:30:00', 3, 3, '2024-12-11 12:30:00', '2024-12-11 12:30:00', NULL),
      (4, '정수영', '010-4567-8901', '최예진', '010-6543-2109', '인천시 송도동 99-10', '인천시 송도동 99-10-402호', '654321987654', '문 앞에 놓아주세요.', 98000, '2024-12-11 14:45:00', 1, 4, '2024-12-11 14:45:00', '2024-12-11 14:45:00', NULL),
      (5, '박준호', '010-5678-9012', '서지원', '010-5432-1098', '경기도 수원시 장안구 영화동 44-22', '경기도 수원시 장안구 영화동 44-22-101호', '321654987321', '주문 취소가 가능하면 취소 부탁드립니다.', 64000, '2024-12-11 16:00:00', 2, 5, '2024-12-11 16:00:00', '2024-12-11 16:00:00', NULL);

-- 포인트 거래 코드 기본 데이터 삽입
INSERT INTO point_transaction_type (name, description)
VALUES
    ('PENDING', '포인트 적립/사용 대기'),
    ('COMPLETED', '포인트 적립/사용 완료'),
    ('CANCELLED', '포인트 적립/사용 취소');

-- 포인트 거래 이력 데이터 삽입
INSERT INTO point_history (member_id, amount, point_transaction_type_id, description, reference_order_id, created_date, last_modified_date, deleted_date)
VALUES
    (1, 1000, 1, '첫 구매 적립', NULL, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (2, -500, 2, '환불로 인한 포인트 차감', 101, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (3, 2000, 1, '추천인 포인트 적립', NULL, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (4, -100, 3, '포인트 취소 요청', 102, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (5, 1500, 2, '상품 구매로 포인트 차감', 103, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL);

-- 고정 포인트 정책 데이터 삽입
INSERT INTO fixed_point_policy (policy_name, description)
VALUES
    ('NEW_SIGNUP', '신규 회원 가입 포인트 지급'),
    ('BIRTHDAY', '생일 포인트 지급');

-- 고정 포인트 금액 데이터 삽입
INSERT INTO fixed_point_amount (fixed_policy_id, point_amount, effective_start_date, effective_end_date)
SELECT id,
       CASE policy_name
           WHEN 'NEW_SIGNUP' THEN 5000
           WHEN 'BIRTHDAY' THEN 2000
           END,
       NOW(),
       NULL
FROM fixed_point_policy;

-- 퍼센트 포인트 정책 데이터 삽입
INSERT INTO percentage_point_policy (member_grade_policy_id)
SELECT id FROM member_grade_policy;

-- 퍼센트 포인트 금액 삽입
INSERT INTO percentage_point_amount (percentage_policy_id, percentage, effective_start_date)
SELECT id,
       CASE grade_name
           WHEN '마루한' THEN 1.00
           WHEN '다소니' THEN 2.00
           WHEN '벗드리' THEN 3.00
           WHEN '나누리' THEN 4.00
           END,
       NOW()
FROM member_grade_policy;


-- 데이터 초기값 설정
INSERT INTO refund_method_code (id, name, description)
VALUES
    ('ORIGINAL_PAYMENT_METHOD', '기존 결제 방식으로 환불', '기존 결제 수단으로 환불을 진행'),
    ('STORE_CREDIT', '스토어 크레딧', '스토어 적립금으로 환불');

-- 약관 메인 데이터 삽입
INSERT INTO terms (name, description)
VALUES
    ('이용약관', '서비스 이용에 관한 일반 약관'),
    ('개인정보처리방침', '개인정보의 수집, 이용, 처리 및 보호에 관한 약관');

-- 약관 버전 데이터 삽입
INSERT INTO terms_version (terms_id, version_no, content, effective_start_date)
SELECT id,
       1,
       '이 약관은 회사의 서비스 사용과 관련된 기본 사항을 규정합니다.',
       NOW()
FROM terms;

-- 서브 약관 데이터 삽입
INSERT INTO sub_terms (terms_id, name, description)
SELECT id,
       '개인정보 이용 동의',
       '서비스 이용 시 개인정보 이용에 대한 동의 조항'
FROM terms WHERE name = '개인정보처리방침';

-- 서브 약관 버전 데이터 삽입
INSERT INTO sub_terms_version (sub_terms_id, version_no, content, effective_start_date)
SELECT id,
       1,
       '서비스 제공을 위해 개인정보 이용에 동의합니다.',
       NOW()
FROM sub_terms;

-- 회원 약관 동의 더미 데이터 삽입
INSERT INTO member_terms_agreement (member_id, agreed_terms_version_id, agreed_sub_terms_version_id, agreed_date, last_modified_date, deleted_date)
VALUES
    (1, 1, 1, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (2, 1, 1, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (3, 2, 1, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (4, 1, NULL, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL),
    (5, 2, 1, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), NULL);

-- 상품 데이터 삽입
INSERT INTO product (
    name, short_description, long_description, main_description, price,
    category_id, status_id, importer, manufacturer, expiration_date,
    size, capacity, ingredients, precautions, sale_date, new, hot, best
) VALUES
      ('녹차', '순수한 녹차 잎', '전통 방식으로 가공한 녹차로, 신선하고 건강한 맛을 제공합니다.', '고급스러운 맛의 순수 녹차, 차 한 잔으로 자연을 느껴보세요.', 5000,
       11, 1, '한국차유통', '차나라', '2025-12-31 00:00:00', '중', '100g', '녹차', '직사광선을 피해서 보관하세요.', '2024-01-01 00:00:00', TRUE, FALSE, TRUE),

      ('홍차', '진한 향과 맛의 홍차', '영국식 전통 홍차로, 우려내면 깊고 진한 향이 퍼집니다.', '진한 홍차의 향기를 즐기세요. 영국식 다도에 적합한 홍차입니다.', 6000,
       12, 2, '영국차수입', '홍차명가', '2026-06-30 00:00:00', '대', '150g', '홍차', '뜨거운 물에 3-5분 우려서 드세요.', '2024-02-01 00:00:00', FALSE, TRUE, FALSE),

      ('루이보스 퓨어 차', '다양한 허브가 어우러진 루이보스차', '허브와 꽃을 혼합하여 만든 건강에 좋은 허브차입니다.', '스트레스를 풀어주는 허브차, 잠자리 전 마시기 좋습니다.', 7000,
       14, 1, '허브유통', '허브가든', '2025-06-30 00:00:00', '소', '50g', '카모마일, 라벤더, 민트', '알레르기 반응을 확인 후 섭취하세요.', '2024-03-01 00:00:00', TRUE, FALSE, FALSE),

      ('유기농 녹차', '유기농으로 재배한 녹차', '자연에서 기른 유기농 녹차, 인공 첨가물이 없습니다.', '100% 유기농으로 재배된 녹차로 건강한 맛을 제공합니다.', 8000,
       10, 1, '유기농차회사', '그린티코퍼레이션', '2025-12-31 00:00:00', '중', '200g', '녹차', '서늘하고 건조한 곳에 보관하세요.', '2024-04-01 00:00:00', FALSE, TRUE, FALSE),

      ('얼그레이', '향긋한 얼그레이 차', '유럽에서 전통적인 방식으로 만든 얼그레이 차입니다.', '얼그레이 특유의 은은한 향을 느끼실 수 있습니다.', 7500,
       13, 3, '프랑스차수입', '벨로르티', '2026-01-01 00:00:00', '소', '75g', '홍차, 베르가못', '직사광선을 피하고 서늘한 곳에 보관하세요.', '2024-05-01 00:00:00', FALSE, FALSE, TRUE);
