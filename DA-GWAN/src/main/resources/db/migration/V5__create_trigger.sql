-- =======================
-- 트리거 생성: 참조가 있는 카테고리 삭제시 에러 발생
-- =======================
DELIMITER $$

CREATE TRIGGER prevent_delete_with_children
    BEFORE UPDATE ON category
    FOR EACH ROW
BEGIN
    IF NEW.deleted_date IS NOT NULL THEN
        IF EXISTS (
            SELECT 1
            FROM category
            WHERE parent_id = OLD.id AND deleted_date IS NULL
        ) THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Cannot delete category with active subcategories.';
        END IF;
    END IF;
END$$

DELIMITER ;