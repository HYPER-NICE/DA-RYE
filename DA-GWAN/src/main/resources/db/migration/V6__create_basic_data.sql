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

-- 루트 카테고리 삽입
INSERT INTO category (name, parent_id)
VALUES
    ('차', NULL),
    ('차기', NULL);

-- 2뎁스 카테고리 삽입
INSERT INTO category (name, parent_id)
SELECT name, id
FROM (
         SELECT '녹차' AS name, id FROM category WHERE name = '차'
         UNION
         SELECT '홍차' AS name, id FROM category WHERE name = '차'
         UNION
         SELECT '다기 세트' AS name, id FROM category WHERE name = '차기'
         UNION
         SELECT '찻잔' AS name, id FROM category WHERE name = '차기'
     ) AS sub_categories;

-- 3뎁스 카테고리 삽입
INSERT INTO category (name, parent_id)
SELECT name, id
FROM (
         -- 차 - 녹차 하위
         SELECT '유기농 녹차' AS name, id FROM category WHERE name = '녹차'
         UNION
         SELECT '보성 녹차' AS name, id FROM category WHERE name = '녹차'

         -- 차 - 홍차 하위
         UNION
         SELECT '다즐링 홍차' AS name, id FROM category WHERE name = '홍차'
         UNION
         SELECT '아쌈 홍차' AS name, id FROM category WHERE name = '홍차'

         -- 차기 - 다기 세트 하위
         UNION
         SELECT '전통 다기 세트' AS name, id FROM category WHERE name = '다기 세트'
         UNION
         SELECT '현대식 다기 세트' AS name, id FROM category WHERE name = '다기 세트'

         -- 차기 - 찻잔 하위
         UNION
         SELECT '도자기 찻잔' AS name, id FROM category WHERE name = '찻잔'
         UNION
         SELECT '유리 찻잔' AS name, id FROM category WHERE name = '찻잔'
     ) AS sub_categories;


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

-- 포인트 거래 코드 기본 데이터 삽입
INSERT INTO point_transaction_type (name, description)
VALUES
    ('PENDING', '포인트 적립/사용 대기'),
    ('COMPLETED', '포인트 적립/사용 완료'),
    ('CANCELLED', '포인트 적립/사용 취소');

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
