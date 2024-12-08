-- =======================
-- 외래 키 추가
-- =======================
ALTER TABLE member
    ADD CONSTRAINT FK_member_grade FOREIGN KEY (grade_id) REFERENCES member_grade (id) ON DELETE RESTRICT;

ALTER TABLE category
    ADD CONSTRAINT FK_category_parent FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE RESTRICT;

ALTER TABLE product
    ADD CONSTRAINT FK_product_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE NO ACTION;

ALTER TABLE inventory
    ADD CONSTRAINT FK_inventory_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE;

ALTER TABLE member_order
    ADD CONSTRAINT FK_member_order_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE NO ACTION;

ALTER TABLE member_order_detail
    ADD CONSTRAINT FK_order_detail_order FOREIGN KEY (order_id) REFERENCES member_order (id) ON DELETE NO ACTION;

ALTER TABLE member_order_detail
    ADD CONSTRAINT FK_order_detail_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION;
