-- 외래 키 추가
ALTER TABLE product
    ADD CONSTRAINT FK_product_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE;

ALTER TABLE inbound
    ADD CONSTRAINT FK_inbound_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE;

ALTER TABLE outbound
    ADD CONSTRAINT FK_outbound_inbound FOREIGN KEY (inbound_id) REFERENCES inbound (id) ON DELETE CASCADE;

ALTER TABLE outbound
    ADD CONSTRAINT FK_outbound_reason FOREIGN KEY (reason_id) REFERENCES outbound_reason (id) ON DELETE CASCADE;

ALTER TABLE member_order
    ADD CONSTRAINT FK_member_order_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE NO ACTION;

ALTER TABLE member_order_detail
    ADD CONSTRAINT FK_order_detail_order FOREIGN KEY (order_id) REFERENCES member_order (id) ON DELETE NO ACTION;

ALTER TABLE member_order_detail
    ADD CONSTRAINT FK_order_detail_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION;

-- 포인트 관련 외래 키 설정
ALTER TABLE member_point
    ADD CONSTRAINT FK_member_point_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE;

ALTER TABLE point_transaction
    ADD CONSTRAINT FK_point_transaction_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE NO ACTION;

ALTER TABLE point_transaction
    ADD CONSTRAINT FK_point_transaction_status FOREIGN KEY (status_id) REFERENCES point_transaction_status (id) ON DELETE NO ACTION;

ALTER TABLE point_transaction
    ADD CONSTRAINT FK_point_transaction_order FOREIGN KEY (reference_order_id) REFERENCES member_order (id) ON DELETE SET NULL;

ALTER TABLE percentage_point_policy
    ADD CONSTRAINT FK_percentage_point_policy_grade FOREIGN KEY (member_grade_id) REFERENCES member_grade (id) ON DELETE CASCADE;


