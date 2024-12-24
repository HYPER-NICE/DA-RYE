package hyper.darye.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 주문 상세 정보를 저장하는 테이블로, 각 주문에 포함된 제품과 그 수량을 관리합니다.
 * ORDER_DETAIL
 */
@Data
public class OrderDetail implements Serializable {
    private Long id;
    private Long orderId;                           // FK
    private Long productId;                         // FK
    private Integer quantity;
    private Integer unitPrice;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;
}