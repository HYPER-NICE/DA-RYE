package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 주문의 배송 정보를 저장하는 테이블로, 주문의 배송 정보를 관리합니다.
 * ORDER_DELIVERY_MAIN
 */
@Data
public class OrderDeliveryMain implements Serializable {
    /**
     * 주문 배송 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 주문 ID
     */
    private Long orderMainId;

    /**
     * 배송 업체 이름
     */
    private String deliveryCompanyName;

    /**
     * 송장 번호
     */
    private String trackingNumber;

    /**
     * 배송 요청 사항
     */
    private String deliveryRequestNote;

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