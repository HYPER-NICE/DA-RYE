-- ========================================================
-- PRODUCT_STATUS
-- ========================================================

INSERT INTO PRODUCT_STATUS (NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE)
VALUES ('판매 대기', '판매 예정중이라 대기가 걸려있는 상태', NOW(), NOW(), NULL, NULL),
       ('판매 중', '현재 판매 중인 상태', NOW(), NOW(), NULL, NULL),
       ('재고없음', '입고된 상품이 다 팔려 재고가 떨어진 상태', NOW(), NOW(), NULL, NULL),
       ('판매 중지', '여러 이유로 판매가 중지된 상태', NOW(), NOW(), NULL, NULL);

-- ========================================================
-- PRODUCT_STATUS
-- ========================================================

INSERT INTO POINT_TRANSACTION_TYPE (CODE_TYPE, CODE_VALUE, NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE,
                                    DELETED_DATE)
VALUES ('POINT_TRANSACTION_TYPE', 'save', '포인트 적립', '고객이 적립한 포인트 거래 코드', NOW(), NOW(), NULL),
       ('POINT_TRANSACTION_TYPE', 'use', '포인트 사용', '고객이 사용한 포인트 거래 코드', NOW(), NOW(), NULL),
       ('POINT_TRANSACTION_TYPE', 'cancel', '포인트 소멸', '미접속 1년 경과로 소멸된 포인트 거래 코드', NOW(), NOW(), NULL);

-- ========================================================
-- PRODUCT_STATUS
-- ========================================================
INSERT INTO ORDER_STATUS_CODE (NAME, DESCRIPTION)
VALUES ('주문 대기', '재고 상황을 파악하고 주문을 다음단계로 넘길 수 있습니다. 현 시스템에서는 재고가 없으면 주문이 불가능하기 때문에 사용하지 않음'),
       ('주문 접수', '고객의 주문이 접수된 상태'),
       ('주문 완료', '고객의 주문이 완료된 상태'),
       ('주문 취소 접수', '고객이 주문을 취소하고 접수된 상태'),
       ('주문 취소 중', '고객이 주문을 취소하고 중인 상태'),
       ('주문 취소 완료', '고객이 주문을 취소하고 완료된 상태');

-- ========================================================
-- DELIVERY_TYPE_CODE
-- ========================================================
INSERT INTO DELIVERY_TYPE_CODE (NAME, DESCRIPTION)
VALUES ('발송', '고객에게 상품을 발송'),
       ('수거', '고객에게 보낸 상품을 수거');

-- ========================================================
-- DELIVERY_STATUS_CODE
-- ========================================================
INSERT INTO DELIVERY_STATUS_CODE (NAME, DESCRIPTION)
VALUES ('배송 준비', '물건이 이동 전 입니다.'),
       ('배송 중', '물건이 이동 중 입니다.'),
       ('배송 완료', '물건이 도착 했습니다.');

-- ========================================================
-- PAYMENT_METHOD_CODE
-- ========================================================
INSERT INTO PAYMENT_METHOD_CODE (NAME, DESCRIPTION)
VALUES ('신용카드', '신용카드를 이용한 결제'),
       ('포인트', '포인트를 이용한 결제'),
       ('무통장 입금', '무통장 입금을 통한 결제'),
       ('HYPER PAY', '하이퍼페이를 이용한 결제');

-- ========================================================
-- PAYMENT_STATUS_CODE
-- ========================================================
INSERT INTO PAYMENT_STATUS_CODE (NAME, DESCRIPTION)
VALUES ('결제 대기', '결제가 대기 중인 상태'),
       ('결제 완료', '결제가 정상적으로 완료된 상태'),
       ('결제 취소', '결제가 취소된 상태'),
       ('환불 대기', '환불이 대기 중인 상태'),
       ('환불 거부', '환불이 거부된 상태'),
       ('환불 완료', '환불이 완료된 상태');


-- ==================================================
-- V6 Board - BOARD
-- ==================================================
INSERT INTO BOARD_CATEGORY_CODE (
    ID, ROOT_CATEGORY, SUB_CATEGORY, ROOT_NAME, ROOT_DESCRIPTION, SUB_NAME, SUB_DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE
) VALUES
      (1, 1, 1, '공지사항', '사이트 전반적인 공지사항', '중요', '중요 공지사항입니다.', NOW(), NOW(), NULL, NULL),
      (2, 1, 2, '공지사항', '사이트 전반적인 공지사항', '일반', '일반 공지사항입니다.', NOW(), NOW(), NULL, NULL),
      (3, 2, 1, 'FAQ', '자주 묻는 질문', '주문/결제', '주문/결제와 관련된 FAQ입니다.', NOW(), NOW(), NULL, NULL),
      (4, 2, 2, 'FAQ', '자주 묻는 질문', '환불/반품/교환', '환불/반품/교환과 관련된 FAQ입니다.', NOW(), NOW(), NULL, NULL),
      (5, 2, 3, 'FAQ', '자주 묻는 질문', '배송', '배송과 관련된 FAQ입니다.', NOW(), NOW(), NULL, NULL),
      (6, 2, 4, 'FAQ', '자주 묻는 질문', '회원/포인트', '회원/포인트와 관련된 FAQ입니다.', NOW(), NOW(), NULL, NULL),
      (7, 2, 5, 'FAQ', '자주 묻는 질문', '사이트 이용/기타', '사이트 이용/기타와 관련된 FAQ입니다.', NOW(), NOW(), NULL, NULL),
      (8, 3, 1, '1대 1 문의', '1대 1 문의 게시판', '미답변', '답변이 되지 않은 1대1 문의입니다.', NOW(), NOW(), NULL, NULL),
      (9, 3, 2, '1대 1 문의', '1대 1 문의 게시판', '답변완료', '답변이 완료된 1대1 문의입니다.', NOW(), NOW(), NULL, NULL);
