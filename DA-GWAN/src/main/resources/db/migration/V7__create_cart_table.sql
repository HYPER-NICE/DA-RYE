-- =========================================
-- 카트 테이블 및 카트 아이템 테이블
-- =========================================

CREATE TABLE cart (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카트 ID (기본 키)',
                      member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                      created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                      last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                      CONSTRAINT FK_cart_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);

CREATE TABLE cart_item (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카트 아이템 ID (기본 키)',
                           cart_id BIGINT NOT NULL COMMENT '카트 ID (외래 키)',
                           product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                           quantity INT NOT NULL DEFAULT 1 COMMENT '수량',
                           created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                           last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                           CHECK (quantity > 0),
                           CONSTRAINT FK_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
                           CONSTRAINT FK_cart_item_product FOREIGN KEY (product_id) REFERENCES product (id)
);
