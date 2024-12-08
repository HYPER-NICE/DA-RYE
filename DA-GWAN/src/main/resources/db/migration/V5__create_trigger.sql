
-- =======================
-- 트리거 생성: 재고 수량 자동 업데이트
-- =======================
DELIMITER $$

CREATE TRIGGER after_inventory_insert
    AFTER INSERT ON inventory
    FOR EACH ROW
BEGIN
    UPDATE product
    SET stock_quantity = stock_quantity + NEW.quantity
    WHERE id = NEW.product_id;
END$$

DELIMITER ;

