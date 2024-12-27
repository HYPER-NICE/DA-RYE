package hyper.darye.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 주문의 배송 정보를 저장하는 테이블로, 주문의 배송 정보를 관리합니다.
 * ORDER_DELIVERY_MAIN
 */
@Data
public class OrderDeliveryMain implements Serializable {
    private Long id;
    private Long orderMainId;                           // FK
    private String deliveryCompanyName;
    private String trackingNumber;
    private String deliveryRequestNote;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;
}