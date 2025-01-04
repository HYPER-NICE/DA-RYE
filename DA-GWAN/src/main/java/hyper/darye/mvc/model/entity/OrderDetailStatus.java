package hyper.darye.mvc.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 주문의 상태 변경 이력을 저장하는 테이블로, 각 주문의 상태 변화를 추적합니다.
 * ORDER_DETAIL_STATUS
 */
@Data
public class OrderDetailStatus implements Serializable {

    private Long id;
    private Long orderDetailId;                         // FK
    private Long orderStatusCodeId;                     // FK
    private LocalDateTime orderDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;
}