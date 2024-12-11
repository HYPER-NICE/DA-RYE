-- 장바구니 더미 데이터 삽입 (차 종류)
INSERT INTO cart (member_id, product_id, product_name, product_price, product_quantity, discount, product_total_price, earning_points, created_date, last_modified_date)
VALUES
    (1, 101, '녹차', 30000, 2, 5000, 55000, 600, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
    (1, 102, '말차', 25000, 1, 3000, 22000, 300, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
    (2, 103, '홍차', 20000, 3, 2000, 58000, 900, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
    (3, 104, '자스민차', 18000, 5, 1000, 85000, 500, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
    (4, 105, '우롱차', 35000, 2, 7000, 63000, 700, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));
