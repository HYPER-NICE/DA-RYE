package hyper.darye.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문의 배송 상태 변경 이력을 저장하는 테이블로, 각 주문의 배송 상태 변화를 추적합니다.
 * ORDER_DELIVERY_STATUS
 */
@Getter
@NoArgsConstructor
public class OrderDeliveryStatus implements Serializable {

    private Long id;
    private Long orderDeliveryMainId;               // FK
    private Long deliveryStatusCodeId;              // FK
    private LocalDateTime deliveryDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;

    public static OrderDeliveryStatus createForAdd(Long orderDeliveryMainId, Long deliveryStatusCodeId, LocalDateTime deliveryDate) {
        OrderDeliveryStatus orderDeliveryStatus = new OrderDeliveryStatus();
        orderDeliveryStatus.orderDeliveryMainId = orderDeliveryMainId;
        orderDeliveryStatus.deliveryStatusCodeId = deliveryStatusCodeId;
        orderDeliveryStatus.deliveryDate = deliveryDate;
        return orderDeliveryStatus;
    }
}