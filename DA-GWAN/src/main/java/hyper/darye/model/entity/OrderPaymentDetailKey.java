package hyper.darye.model.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 주문의 결제 상세 정보를 저장하는 테이블로, 각 주문의 결제 금액과 결제 날짜를 관리합니다.
 * ORDER_PAYMENT_DETAIL 기본키만 모아서
 */
@Data
public class OrderPaymentDetailKey implements Serializable {

    private Long orderPaymentMainId;                    // FK
    private Long paymentMethodCodeId;                   // FK
    private Long orderPaymentStatusCodeId;              // FK
    private static final long serialVersionUID = 1L;
}