package hyper.darye.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 주문의 결제 상세 정보를 저장하는 테이블로, 각 주문의 결제 금액과 결제 날짜를 관리합니다.
 * ORDER_PAYMENT_DETAIL
 */
@Data
public class OrderPaymentDetailKey implements Serializable {
    /**
     * 주문 결제 상세 고유 ID (기본 키)
     */
    private Long orderPaymentMainId;

    /**
     * 결제 방법 코드 ID
     */
    private Long paymentMethodCodeId;

    /**
     * 주문 결제 상태 코드 ID
     */
    private Long orderPaymentStatusCodeId;

    private static final long serialVersionUID = 1L;
}