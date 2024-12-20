-- ==================================================
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================

-- 테이블 비우기
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE BOARD;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- V6 Board - BOARD
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
-- 테이블 truncate 용도 쿼리, 옵션 비활성화
-- ==================================================

-- 테이블 비우기
-- 1. 외래 키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 테이블 비우기
TRUNCATE TABLE BOARD_CATEGORY_CODE;

-- 3. 외래 키 체크 활성화
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- V6 Board - BOARD_IMAGE
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