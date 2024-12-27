package hyper.darye.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 주문 상태 코드를 저장하며, 주문의 현재 상태를 관리하는 테이블입니다. 예를 들어 결제 완료, 배송 준비 등의 상태를 포함합니다.
 * ORDER_STATUS_CODE
 */
@Data
public class OrderStatusCode implements Serializable {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;
}