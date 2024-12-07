-- 카테고리 테이블 생성
CREATE TABLE category
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(255) NOT NULL,
    parent_id          BIGINT NULL,
    created_date       TIMESTAMP DEFAULT NOW() NULL,
    last_modified_date TIMESTAMP DEFAULT NOW() NULL,
    CONSTRAINT PK_category PRIMARY KEY (id)
);

-- 멤버 테이블 생성
CREATE TABLE member
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    address            VARCHAR(255) NULL,
    mobile             VARCHAR(255) NULL,
    created_date       TIMESTAMP DEFAULT NOW() NULL,
    last_modified_date TIMESTAMP DEFAULT NOW() NULL,
    CONSTRAINT PK_member PRIMARY KEY (id)
);

-- 주문 테이블 생성
CREATE TABLE member_order
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    member_id          BIGINT NULL,
    total_price        INT NOT NULL,
    order_date         TIMESTAMP DEFAULT NOW() NOT NULL,
    delivery_address   VARCHAR(255) NOT NULL,
    payment_method     VARCHAR(255) NOT NULL,
    delivery_status    ENUM('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL,
    status             ENUM('NEW', 'PROCESSING', 'COMPLETED', 'CANCELLED') NOT NULL,
    created_date       TIMESTAMP DEFAULT NOW() NULL,
    last_modified_date TIMESTAMP DEFAULT NOW() NULL,
    CONSTRAINT PK_member_order PRIMARY KEY (id)
);

-- 주문 상세 테이블 생성
CREATE TABLE member_order_detail
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    order_id           BIGINT NULL,
    product_id         BIGINT NULL,
    amount             INT NOT NULL,
    created_date       TIMESTAMP DEFAULT NOW() NULL,
    last_modified_date TIMESTAMP DEFAULT NOW() NULL,
    CONSTRAINT PK_member_order_detail PRIMARY KEY (id)
);

-- 제품 테이블 생성
CREATE TABLE product
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(255) NOT NULL,
    description        LONGTEXT NULL,
    price              INT DEFAULT 0 NOT NULL,
    stock_quantity     INT DEFAULT 0 NOT NULL,
    category_id        BIGINT NOT NULL,
    created_date       TIMESTAMP DEFAULT NOW() NULL,
    last_modified_date TIMESTAMP DEFAULT NOW() NULL,
    CONSTRAINT PK_product PRIMARY KEY (id)
);

-- UNIQUE 제약조건 추가
ALTER TABLE category
    ADD CONSTRAINT UK_category_name UNIQUE (name);

ALTER TABLE member
    ADD CONSTRAINT UK_member_email UNIQUE (email);

-- FOREIGN KEY 제약조건 추가
ALTER TABLE product
    ADD CONSTRAINT FK_product_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE NO ACTION;

ALTER TABLE category
    ADD CONSTRAINT FK_category_parent FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE NO ACTION;

ALTER TABLE member_order_detail
    ADD CONSTRAINT FK_order_detail_order FOREIGN KEY (order_id) REFERENCES member_order (id) ON DELETE NO ACTION;

ALTER TABLE member_order
    ADD CONSTRAINT FK_member_order_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE NO ACTION;

ALTER TABLE member_order_detail
    ADD CONSTRAINT FK_order_detail_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION;

-- 인덱스 생성
CREATE INDEX IDX_product_category ON product (category_id);

CREATE INDEX IDX_category_parent ON category (parent_id);

CREATE INDEX IDX_order_detail_order ON member_order_detail (order_id);

CREATE INDEX IDX_member_order_member ON member_order (member_id);

CREATE INDEX IDX_order_detail_product ON member_order_detail (product_id);
