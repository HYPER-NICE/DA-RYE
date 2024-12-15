-- =========================================
-- 과거의 기본 데이터
-- 잘 보관하기
-- =========================================
# -- 상품 상태 코드 기본 데이터 삽입
# INSERT INTO PRODUCT_STATUS_CODE (NAME, DESCRIPTION)
# VALUES ('ON_SALE', '판매중'),
#        ('SOLD OUT', '품절'),
#        ('DISCONTINUED', '단종');
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

-- 포인트 거래 코드 기본 데이터 삽입
INSERT INTO COMMON_CODE (CODE, NAME, DESCRIPTION)
VALUES ('M1001', 'PENDING', '물건 구매에 의한 적립'),
       ('M1002', 'COMPLETED', '사용'),
       ('M1003', 'CANCELLED', '환불에 의한 적립'),
       ('M1004', 'EXPIRED', '기한 만료'),
       ('M1005', 'REFUNDED', '이벤트에 의한 적립');


-- =========================================
-- 기본 데이터 삽입
-- =========================================

-- 공통 코드 삽입
INSERT INTO COMMON_CODE (CODE, NAME, DESCRIPTION)
VALUES ('P0301', '입고 상태(입고 완료)', '입고가 완료되어 입고 로케이션에 배치해둔 상태'),
       ('P0302', '입고 상태(반품 수거)', '반품으로 인해 다시 입고가 되었음을 나타내는 상태'),
       ('P0303', '입고 상태(교환 수거)', '교환으로 인해 다시 입고가 되었음을 나타내는 상태');

-- =========================================
-- 주문 코드
-- =========================================

# INSERT INTO ORDER_STATUS_CODE (NAME, DESCRIPTION)
# VALUES ('PAYMENT_PENDING', '결제 대기'),
#        ('PAYMENT_COMPLETED', '결제 완료'),
#        ('CANCELLATION_REQUESTED', '취소 요청 대기'),
#        ('CANCELLATION_COMPLETED', '취소 완료'),
#        ('PARTIAL_CANCELLATION_COMPLETED', '주문 일부 상품만 취소 완료'),
#        ('FULL_CANCELLATION_COMPLETED', '주문 전체 취소 완료');
#
# -- 배송 상태 기본 데이터 삽입
# INSERT INTO DELIVERY_STATUS_CODE (NAME, DESCRIPTION)
# VALUES ('PREPARING_SHIPMENT', '배송 준비 중'),
#        ('SHIPPED', '배송 중'),
#        ('DELIVERED', '배송 완료'),
#        ('RETURN_REQUESTED', '반품 요청 대기'),
#        ('RETURN_COMPLETED', '반품 완료'),
#        ('EXCHANGE_REQUESTED', '교환 요청 대기'),
#        ('EXCHANGE_COMPLETED', '교환 완료');
#
# -- 결제 코드 기본 데이터 삽입
# INSERT INTO PAYMENT_METHOD_CODE (NAME, DESCRIPTION)
# VALUES ('POINT', '포인트 결제'),
#        ('BANK_TRANSFER', '계좌 이체 결제'),
#        ('CANCEL', '결제 취소');
# -- 포인트 결제, 카드 결제, 취소 예정, 취소 완료, 이제 예정, 결제 예정, 카드결제 완료
#
# -- 결제 상태 코드 초기 데이터 삽입
# INSERT INTO PAYMENT_STATUS_CODE (NAME, DESCRIPTION)
# VALUES ('PENDING', '결제가 대기 상태입니다.'),
#        ('COMPLETED', '결제가 성공적으로 완료되었습니다.'),
#        ('FAILED', '결제가 실패했습니다.'),
#        ('CANCELED', '결제가 취소되었습니다.'),
#        ('REFUNDED', '결제가 환불되었습니다.');

-- 공통 코드 테이블에 데이터 삽입

-- 주문 상태 기본 데이터
INSERT INTO COMMON_CODE (CODE, NAME, DESCRIPTION)
VALUES ('O0101', 'PAYMENT_PENDING', '결제 대기'),
       ('O0102', 'PAYMENT_COMPLETED', '결제 완료'),
       ('O0103', 'CANCELLATION_REQUESTED', '취소 요청 대기'),
       ('O0104', 'CANCELLATION_COMPLETED', '취소 완료'),
       ('O0105', 'PARTIAL_CANCELLATION_COMPLETED', '주문 일부 상품만 취소 완료'),
       ('O0106', 'FULL_CANCELLATION_COMPLETED', '주문 전체 취소 완료');

-- 배송 상태 기본 데이터
INSERT INTO COMMON_CODE (CODE, NAME, DESCRIPTION)
VALUES ('O0201', 'PREPARING_SHIPMENT', '배송 준비 중'),
       ('O0202', 'SHIPPED', '배송 중'),
       ('O0203', 'DELIVERED', '배송 완료'),
       ('O0204', 'RETURN_REQUESTED', '반품 요청 대기'),
       ('O0205', 'RETURN_COMPLETED', '반품 완료'),
       ('O0206', 'EXCHANGE_REQUESTED', '교환 요청 대기'),
       ('O0207', 'EXCHANGE_COMPLETED', '교환 완료');

-- 결제 코드 기본 데이터
INSERT INTO COMMON_CODE (CODE, NAME, DESCRIPTION)
VALUES ('O0301', 'POINT', '포인트 결제'),
       ('O0302', 'BANK_TRANSFER', '계좌 이체 결제'),
       ('O0303', 'CANCEL', '결제 취소');

-- 결제 상태 코드 초기 데이터
INSERT INTO COMMON_CODE (CODE, NAME, DESCRIPTION)
VALUES ('O0401', 'PENDING', '결제가 대기 상태입니다.'),
       ('O0402', 'COMPLETED', '결제가 성공적으로 완료되었습니다.'),
       ('O0403', 'FAILED', '결제가 실패했습니다.'),
       ('O0404', 'CANCELED', '결제가 취소되었습니다.'),
       ('O0405', 'REFUNDED', '결제가 환불되었습니다.');