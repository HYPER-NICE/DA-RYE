package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 주문의 배송 상태 변경 이력을 저장하는 테이블로, 각 주문의 배송 상태 변화를 추적합니다.
 * ORDER_DELIVERY_STATUS
 */
@Data
public class OrderDeliveryStatus implements Serializable {
    /**
     * 주문 배송 상태 변경 이력 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 주문 배송 ID
     */
    private Long orderDeliveryMainId;

    /**
     * 배송 상태 코드 ID
     */
    private Long deliveryStatusCodeId;

    /**
     * 배송 상태 변경 시간, 예, 배송 출발 시간, 배송 완료 시간
     */
    private Date deliveryDate;

    /**
     * 레코드 생성 날짜 및 시간
     */
    private Date createdDate;

    /**
     * 마지막 수정된 날짜 및 시간
     */
    private Date lastModifiedDate;

    /**
     * 레코드를 마지막으로 수정한 회원 ID
     */
    private Long lastModifiedMember;

    /**
     * 레코드가 삭제된 날짜 (논리 삭제)
     */
    private Date deletedDate;

    private static final long serialVersionUID = 1L;
}