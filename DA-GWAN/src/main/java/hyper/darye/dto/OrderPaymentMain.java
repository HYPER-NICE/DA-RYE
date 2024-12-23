package hyper.darye.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 주문의 결제 정보를 저장하는 테이블로, 주문의 결제 방법과 결제 금액을 관리합니다.
 * ORDER_PAYMENT_MAIN
 */
@Data
public class OrderPaymentMain implements Serializable {
    private Long id;
    private Long orderId;
    private Integer totalAmount;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;
}