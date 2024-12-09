-- =========================================
-- 기초 데이터 삽입 (예시)
-- =========================================

-- 기본 멤버 등급 데이터 삽입
INSERT INTO member_grade_policy (grade_name, min_amount, max_amount, period_days, description)
VALUES
    ('나누리', 100000, 999999999, 360, '최근 360일 동안 10만원 이상 구매 고객'),
    ('벗드리', 50000, 99999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객'),
    ('다소니', 10000, 49999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객'),
    ('마루한', 0, 9999, 90, '최근 90일 동안 1만원 미만 구매 고객');

-- 기본 입고 사유 데이터 삽입
INSERT INTO inbound_reason (name, description)
VALUES
    ('PURCHASE', '공급업체로부터 신규 구매 입고'),
    ('CUSTOMER_RETURN', '고객 반품 상품 재입고'),
    ('PROMOTIONAL_EVENT', '프로모션 목적 재고 확보 입고'),
    ('CORRECTION', '재고 오류 정정 입고');

-- 기본 출고 사유 데이터 삽입
INSERT INTO outbound_reason (name, description)
VALUES
    ('DAMAGED', '상품 파손 폐기 출고'),
    ('DONATION', '기부 목적 출고'),
    ('TRANSFER_OUT', '외부 창고 이동 출고'),
    ('INTERNAL_USE', '사내 용도 사용 출고');

-- 기본 주문 상태 데이터 삽입
INSERT INTO order_status (name, description)
VALUES
    ('PAYMENT_PENDING', '결제 대기'),
    ('PAYMENT_COMPLETED', '결제 완료'),
    ('CANCELLATION_REQUESTED', '취소 요청 대기'),
    ('CANCELLATION_COMPLETED', '취소 완료'),
    ('REFUND_REQUESTED', '환불 요청 대기'),
    ('REFUNDED', '환불 완료');

-- 기본 배송 상태 데이터 삽입
INSERT INTO delivery_status (name, description)
VALUES
    ('PREPARING_SHIPMENT', '배송 준비 중'),
    ('SHIPPED', '배송 중'),
    ('DELIVERED', '배송 완료'),
    ('RETURN_PREPARING', '수거 준비 중'),
    ('RETURN_PICKUP', '수거 중'),
    ('RETURN_COMPLETED', '수거 완료');

-- 기본 결제 수단 데이터 삽입
INSERT INTO payment_method (name, description)
VALUES
    ('CREDIT_CARD', '신용 카드 결제'),
    ('BANK_TRANSFER', '계좌이체');

-- 기본 포인트 거래 상태 데이터 삽입
INSERT INTO point_transaction_status (name, description)
VALUES
    ('PENDING', '포인트 적립/사용 대기'),
    ('COMPLETED', '포인트 적립/사용 완료'),
    ('CANCELLED', '포인트 적립/사용 취소');

-- 등급별 포인트 적립율 (예제)
INSERT INTO percentage_point_policy (percentage_point_policy.member_grade_policy_id, percentage)
SELECT id, CASE grade_name
               WHEN '마루한' THEN 1.00
               WHEN '다소니' THEN 2.00
               WHEN '벗드리' THEN 3.00
               WHEN '나누리' THEN 4.00
    END
FROM member_grade_policy;

-- 고정 포인트 정책 (예제)
INSERT INTO fixed_point_policy (policy_name, description, point_amount)
VALUES
    ('FRIEND_INVITE', '친구 초대 포인트 지급', 500),
    ('NEW_SIGNUP', '신규 회원 가입 포인트 지급', 1000),
    ('BIRTHDAY', '생일 포인트 지급', 2000);