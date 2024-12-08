-- =======================
-- 외래 키 추가
-- =======================
ALTER TABLE product
    ADD CONSTRAINT FK_product_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE;

ALTER TABLE inbound
    ADD CONSTRAINT FK_inbound_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE;

ALTER TABLE outbound
    ADD CONSTRAINT FK_outbound_inbound FOREIGN KEY (inbound_id) REFERENCES inbound (id) ON DELETE CASCADE;

ALTER TABLE outbound
    ADD CONSTRAINT FK_outbound_product FOREIGN KEY (reason_id) REFERENCES outbound_reason (id) ON DELETE CASCADE;

ALTER TABLE member_order
    ADD CONSTRAINT FK_member_order_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE NO ACTION;

ALTER TABLE member_order_detail
    ADD CONSTRAINT FK_order_detail_order FOREIGN KEY (order_id) REFERENCES member_order (id) ON DELETE NO ACTION;

ALTER TABLE member_order_detail
    ADD CONSTRAINT FK_order_detail_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION;
