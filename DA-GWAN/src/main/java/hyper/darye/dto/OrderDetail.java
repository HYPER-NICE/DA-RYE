package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 주문 상세 정보를 저장하는 테이블로, 각 주문에 포함된 제품과 그 수량을 관리합니다.
 * ORDER_DETAIL
 */
@Data
public class OrderDetail implements Serializable {
    /**
     * 주문 상세 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 주문 ID
     */
    private Long orderId;

    /**
     * 제품 ID
     */
    private Long productId;

    /**
     * 구매 수량
     */
    private Integer quantity;

    /**
     * 제품 단가
     */
    private Integer unitPrice;

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