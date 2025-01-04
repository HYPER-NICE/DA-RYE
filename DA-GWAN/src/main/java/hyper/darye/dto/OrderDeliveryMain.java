package hyper.darye.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 주문의 배송 정보를 저장하는 테이블로, 주문의 배송 정보를 관리합니다.
 * ORDER_DELIVERY_MAIN
 */
@Getter
@NoArgsConstructor
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
     * 주문 유형 코드 ID
     */
    private Long orderTypeCodeId;

    /**
     * 수령인 이름
     */
    private String receiverName;

    /**
     * 수령인 연락처
     */
    private String receiverContact;

    /**
     * 배송 기본 주소
     */
    private String deliveryAddress;

    /**
     * 배송 상세 주소
     */
    private String deliveryDetailAddress;

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
    private LocalDateTime createdDate;

    /**
     * 마지막 수정된 날짜 및 시간
     */
    private LocalDateTime lastModifiedDate;

    /**
     * 레코드를 마지막으로 수정한 회원 ID
     */
    private Long lastModifiedMember;

    /**
     * 레코드가 삭제된 날짜 (논리 삭제)
     */
    private LocalDateTime deletedDate;

    private static final long serialVersionUID = 1L;

    public static OrderDeliveryMain createForAdd(Long orderId, String deliveryCompanyName, String trackingNumber, String deliveryRequestNote) {
        OrderDeliveryMain orderDeliveryMain = new OrderDeliveryMain();
        orderDeliveryMain.orderMainId = orderId;
        orderDeliveryMain.deliveryCompanyName = deliveryCompanyName;
        orderDeliveryMain.trackingNumber = trackingNumber;
        orderDeliveryMain.deliveryRequestNote = deliveryRequestNote;
        return orderDeliveryMain;
    }
}