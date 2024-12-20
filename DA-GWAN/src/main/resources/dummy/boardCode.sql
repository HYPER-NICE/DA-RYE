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
-- V6 Board - BOARD
-- ==================================================

-- 게시판 카테고리 코드 데이터 삽입
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

