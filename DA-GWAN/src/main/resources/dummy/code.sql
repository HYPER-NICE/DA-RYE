-- ========================================================
-- PRODUCT_STATUS
-- ========================================================

INSERT INTO PRODUCT_STATUS (
    NAME, DESCRIPTION, CREATED_DATE, LAST_MODIFIED_DATE, LAST_MODIFIED_MEMBER, DELETED_DATE
) VALUES
      ('판매 대기', '판매 예정중이라 대기가 걸려있는 상태', NOW(), NOW(), NULL, NULL),
      ('판매 중', '현재 판매 중인 상태', NOW(), NOW(), NULL, NULL),
      ('재고없음', '입고된 상품이 다 팔려 재고가 떨어진 상태', NOW(), NOW(), NULL, NULL),
      ('판매 중지', '여러 이유로 판매가 중지된 상태', NOW(), NOW(), NULL, NULL);
select * from PRODUCT_STATUS;