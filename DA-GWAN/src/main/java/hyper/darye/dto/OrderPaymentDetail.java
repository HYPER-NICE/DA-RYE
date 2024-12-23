package hyper.darye.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 주문의 결제 상세 정보를 저장하는 테이블로, 각 주문의 결제 금액과 결제 날짜를 관리합니다.
 * ORDER_PAYMENT_DETAIL
 */
@Data
public class OrderPaymentDetail extends OrderPaymentDetailKey implements Serializable {
    private String approvalNumber;
    private Integer paymentAmount;
    private LocalDateTime paymentDate;
    private String paymentComment;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;
}