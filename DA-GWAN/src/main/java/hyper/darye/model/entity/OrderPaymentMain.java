package hyper.darye.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 주문의 결제 정보를 저장하는 테이블로, 주문의 결제 방법과 결제 금액을 관리합니다.
 * ORDER_PAYMENT_MAIN
 */
@Data
public class OrderPaymentMain implements Serializable {
    /**
     * 주문 결제 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 주문 ID
     */
    private Long orderId;

    /**
     * 총 결제 금액
     */
    private Integer totalAmount;

    /**
     * 레코드 생성 날짜 및 시간
     */
    private LocalDateTime createdDate;

    /**
     * 마지막 수정된 날짜 및 시간
     */
    private LocalDateTime lastModifiedDate;

    /**
     * 레코드를 마지막으로 수정한 회원 ID
     */
    private Long lastModifiedMember;

    /**
     * 레코드가 삭제된 날짜 (논리 삭제)
     */
    private LocalDateTime deletedDate;

    private static final long serialVersionUID = 1L;
}