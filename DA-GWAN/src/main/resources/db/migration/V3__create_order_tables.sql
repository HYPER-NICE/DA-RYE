-- =========================================
-- 3. 주문/결제 관련 코드 테이블
-- =========================================

-- 주문 코드 테이블
CREATE TABLE order_code (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 코드 ID (기본 키)',
                            name VARCHAR(50) NOT NULL UNIQUE COMMENT '주문 코드 이름',
                            description VARCHAR(255) NULL COMMENT '주문 코드 설명',
                            created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                            last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                            deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 배송 코드 테이블
CREATE TABLE delivery_code (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '배송 코드 ID (기본 키)',
                               name VARCHAR(50) NOT NULL UNIQUE COMMENT '배송 코드 이름',
                               description VARCHAR(255) NULL COMMENT '배송 코드 설명',
                               created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                               last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                               deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 주문 메인 테이블
CREATE TABLE member_order_main (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 ID (기본 키)',
                                   member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                                   orderer_name VARCHAR(255) NOT NULL COMMENT '주문자 이름',
                                   orderer_contact VARCHAR(50) NOT NULL COMMENT '주문자 연락처',
                                   receiver_name VARCHAR(255) NOT NULL COMMENT '수령인 이름',
                                   receiver_contact VARCHAR(50) NOT NULL COMMENT '수령인 연락처',
                                   delivery_address VARCHAR(255) NOT NULL COMMENT '배송 기본 주소',
                                   delivery_detail_address VARCHAR(255) NULL COMMENT '배송 상세 주소',
                                   tracking_number VARCHAR(50) NULL COMMENT '송장 번호',
                                   delivery_request_note VARCHAR(255) NULL COMMENT '배송 요청 사항',
                                   total_price INT NOT NULL COMMENT '실제 결제 금액',
                                   order_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '주문 날짜',
                                   delivery_code_id BIGINT NOT NULL COMMENT '배송 코드 ID (외래 키)',
                                   order_code_id BIGINT NOT NULL COMMENT '주문 코드 ID (외래 키)',
                                   created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                   last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                   deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                   CONSTRAINT FK_member_order_main_member FOREIGN KEY (member_id) REFERENCES member (id),
                                   CONSTRAINT FK_member_order_main_delivery_code FOREIGN KEY (delivery_code_id) REFERENCES delivery_code (id),
                                   CONSTRAINT FK_member_order_main_order_code FOREIGN KEY (order_code_id) REFERENCES order_code (id)
);

-- 주문 메인 이력 테이블
CREATE TABLE member_order_main_history (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 메인 이력 ID (기본 키)',
                                           member_order_main_id BIGINT NOT NULL COMMENT '원래 주문 메인 ID',
                                           member_id BIGINT NOT NULL COMMENT '회원 ID (외래 키)',
                                           orderer_name VARCHAR(255) NOT NULL COMMENT '주문자 이름',
                                           orderer_contact VARCHAR(50) NOT NULL COMMENT '주문자 연락처',
                                           receiver_name VARCHAR(255) NOT NULL COMMENT '수령인 이름',
                                           receiver_contact VARCHAR(50) NOT NULL COMMENT '수령인 연락처',
                                           delivery_address VARCHAR(255) NOT NULL COMMENT '배송 기본 주소',
                                           delivery_detail_address VARCHAR(255) NULL COMMENT '배송 상세 주소',
                                           tracking_number VARCHAR(50) NULL COMMENT '송장 번호',
                                           delivery_request_note VARCHAR(255) NULL COMMENT '배송 요청 사항',
                                           total_price INT NOT NULL COMMENT '실제 결제 금액',
                                           order_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL COMMENT '주문 날짜',
                                           delivery_code_id BIGINT NOT NULL COMMENT '배송 코드 ID (외래 키)',
                                           order_code_id BIGINT NOT NULL COMMENT '주문 코드 ID (외래 키)',
                                           operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형(INSERT/UPDATE/DELETE)',
                                           history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',
                                           deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                           CONSTRAINT FK_member_order_main_history_order FOREIGN KEY (member_order_main_id) REFERENCES member_order_main (id) ON DELETE CASCADE
);


CREATE TABLE member_order_detail (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 상세 ID (기본 키)',
                                     order_id BIGINT NOT NULL COMMENT '주문 ID (외래 키)',
                                     product_id BIGINT NOT NULL COMMENT '제품 ID (외래 키)',
                                     unit_price INT NOT NULL COMMENT '제품 단가',
                                     amount INT NOT NULL COMMENT '구매 수량',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                     CONSTRAINT FK_member_order_detail_order FOREIGN KEY (order_id) REFERENCES member_order_main (id) ON DELETE CASCADE,
                                     CONSTRAINT FK_member_order_detail_product FOREIGN KEY (product_id) REFERENCES product (id)
);

-- 사후 처리 상태 코드 테이블
CREATE TABLE after_sales_status_code (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(50) NOT NULL UNIQUE COMMENT '사후 처리 상태 이름 (예: REQUESTED, APPROVED, REJECTED, COMPLETED)',
                                         description VARCHAR(255) NULL,
                                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                                         deleted_date DATETIME(6) DEFAULT NULL
);

-- 사후 처리 사유 코드 테이블
CREATE TABLE after_sales_reason_code (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(50) NOT NULL UNIQUE COMMENT '사유 이름 (예: 단순 변심, 상품 불량 등)',
                                         description VARCHAR(255) NULL,
                                         created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                                         last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                                         deleted_date DATETIME(6) DEFAULT NULL
);

-- after_sales_type : 'CANCELLATION', 'RETURN', 'EXCHANGE', 'REFUND' 등
CREATE TABLE order_after_sales_main (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사후 처리 메인 ID',
                                        member_order_main_id BIGINT NOT NULL COMMENT '연관된 주문 메인 ID',
                                        after_sales_type VARCHAR(50) NOT NULL COMMENT '사후 처리 유형 (CANCELLATION/RETURN/EXCHANGE/REFUND)',
                                        after_sales_status_code_id BIGINT NOT NULL COMMENT '사후 처리 상태 코드 ID (외래 키)',
                                        request_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '요청 날짜',
                                        approved_date DATETIME(6) DEFAULT NULL COMMENT '승인 날짜',
                                        completed_date DATETIME(6) DEFAULT NULL COMMENT '완료 날짜',
                                        created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                                        last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                                        deleted_date DATETIME(6) DEFAULT NULL,
                                        CONSTRAINT FK_after_sales_main_order FOREIGN KEY (member_order_main_id) REFERENCES member_order_main (id) ON DELETE CASCADE,
                                        CONSTRAINT FK_after_sales_main_status FOREIGN KEY (after_sales_status_code_id) REFERENCES after_sales_status_code (id)
);

CREATE TABLE order_after_sales_detail (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사후 처리 상세 ID',
                                          after_sales_main_id BIGINT NOT NULL COMMENT '사후 처리 메인 ID (외래 키)',
                                          member_order_detail_id BIGINT NOT NULL COMMENT '주문 상세 ID (외래 키)',
                                          after_sales_reason_code_id BIGINT NOT NULL COMMENT '사유 코드 ID (외래 키)',
                                          amount INT NOT NULL COMMENT '처리 수량 (취소/반품/교환 수량)',
                                          refund_amount INT NULL COMMENT '환불 금액 (환불 시 사용)',
                                          exchange_product_id BIGINT NULL COMMENT '교환 대상 제품 ID (교환 시 사용)',
                                          comment TEXT NULL COMMENT '추가 설명(옵션)',
                                          created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                                          last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                                          deleted_date DATETIME(6) DEFAULT NULL,
                                          CONSTRAINT FK_after_sales_detail_main FOREIGN KEY (after_sales_main_id) REFERENCES order_after_sales_main (id) ON DELETE CASCADE,
                                          CONSTRAINT FK_after_sales_detail_order_detail FOREIGN KEY (member_order_detail_id) REFERENCES member_order_detail (id) ON DELETE CASCADE,
                                          CONSTRAINT FK_after_sales_detail_reason FOREIGN KEY (after_sales_reason_code_id) REFERENCES after_sales_reason_code (id)
);


-- 결제 방법 코드 테이블
CREATE TABLE payment_method_code (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 코드 ID (기본 키)',
                              name VARCHAR(50) NOT NULL UNIQUE COMMENT '결제 코드 이름',
                              description VARCHAR(255) NULL COMMENT '결제 코드 설명',
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 결제 상태 코드 테이블
CREATE TABLE payment_status_code (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 상태 코드 ID (기본 키)',
                                     name VARCHAR(50) NOT NULL UNIQUE COMMENT '결제 상태 코드 이름',
                                     description VARCHAR(255) NULL COMMENT '결제 상태 코드 설명',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 결제 메인 테이블
CREATE TABLE payment_main (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 메인 ID (기본 키)',
                              order_id BIGINT NOT NULL COMMENT '연관된 주문 ID (외래 키)',
                              total_amount INT NOT NULL COMMENT '총 결제 금액',
                              payment_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '결제 날짜',
                              payment_status_code BIGINT NOT NULL ,
                              created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                              last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                              deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',

                              CONSTRAINT FK_payment_main_order FOREIGN KEY (order_id) REFERENCES member_order_main (id) ON DELETE CASCADE
);

-- 결제 메인 이력 테이블
CREATE TABLE payment_main_history (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 메인 이력 ID (기본 키)',
                                      payment_main_id BIGINT NOT NULL COMMENT '원본 결제 메인 ID',
                                      order_id BIGINT NOT NULL COMMENT '연관된 주문 ID (외래 키)',
                                      total_amount INT NOT NULL COMMENT '총 결제 금액',
                                      payment_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '결제 날짜',
                                      payment_status_code BIGINT NOT NULL COMMENT '결제 상태 코드 (외래 키)',
                                      created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                      last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                      deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',
                                      operation_type VARCHAR(10) NOT NULL COMMENT '조작 유형 (INSERT/UPDATE/DELETE)',
                                      history_created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '이력 생성 날짜',

                                      CONSTRAINT FK_payment_main_history_main FOREIGN KEY (payment_main_id) REFERENCES payment_main (id) ON DELETE CASCADE
);



-- 결제 상세 테이블
CREATE TABLE payment_detail (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 상세 ID (기본 키)',
                                payment_main_id BIGINT NOT NULL COMMENT '연관된 결제 메인 ID (외래 키)',
                                payment_method_code_id BIGINT NOT NULL COMMENT '결제 코드 ID (외래 키)',
                                amount INT NOT NULL COMMENT '해당 수단으로 결제한 금액',
                                created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',

                                CONSTRAINT FK_payment_detail_main FOREIGN KEY (payment_main_id) REFERENCES payment_main (id) ON DELETE CASCADE,
                                CONSTRAINT FK_payment_detail_payment_method_code FOREIGN KEY (payment_method_code_id) REFERENCES payment_method_code (id) ON DELETE CASCADE
);

-- 환불 금액 지급 방식
CREATE TABLE refund_method_code (
                                    id VARCHAR(50) PRIMARY KEY COMMENT '환불 방식 코드 ID',
                                    name VARCHAR(50) NOT NULL UNIQUE COMMENT '환불 방식 이름',
                                    description VARCHAR(255) NULL COMMENT '환불 방식 설명',
                                    created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                    last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                    deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜'
);

-- 결제 환불 메인 테이블
CREATE TABLE payment_refund_main (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 환불 메인 ID (기본 키)',
                                     payment_main_id BIGINT NOT NULL COMMENT '연관된 결제 메인 ID (외래 키)',
                                     refund_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '환불 발생 날짜',
                                     refund_status_code_id BIGINT NOT NULL COMMENT '환불 상태 코드 ID (외래 키)',
                                     created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                     last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                     deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',

                                     CONSTRAINT FK_refund_main_payment FOREIGN KEY (payment_main_id) REFERENCES payment_main (id) ON DELETE CASCADE,
                                     CONSTRAINT FK_refund_main_status FOREIGN KEY (refund_status_code_id) REFERENCES payment_status_code (id)
);

-- 결제 환불 상세 테이블
CREATE TABLE payment_refund_detail (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 환불 상세 ID (기본 키)',
                                       payment_refund_main_id BIGINT NOT NULL COMMENT '결제 환불 메인 ID (외래 키)',
                                       payment_detail_id BIGINT NOT NULL COMMENT '결제 상세 ID (외래 키)',
                                       refund_amount INT NOT NULL COMMENT '환불 금액',
                                       refund_method_code_id VARCHAR(50) NOT NULL COMMENT '환불 방식 코드 (외래 키)',
                                       comment TEXT NULL COMMENT '추가 설명 (옵션)',
                                       created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성 날짜',
                                       last_modified_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정 날짜',
                                       deleted_date DATETIME(6) DEFAULT NULL COMMENT '삭제 날짜',

                                       CONSTRAINT FK_refund_detail_main FOREIGN KEY (payment_refund_main_id) REFERENCES payment_refund_main (id) ON DELETE CASCADE,
                                       CONSTRAINT FK_refund_detail_payment FOREIGN KEY (payment_detail_id) REFERENCES payment_detail (id) ON DELETE CASCADE,
                                       CONSTRAINT FK_refund_method FOREIGN KEY (refund_method_code_id) REFERENCES refund_method_code (id),
                                       CONSTRAINT chk_valid_refund_amount CHECK (refund_amount > 0)
);
