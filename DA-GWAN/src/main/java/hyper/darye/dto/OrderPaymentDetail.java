package hyper.darye.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 주문의 결제 상세 정보를 저장하는 테이블로, 각 주문의 결제 금액과 결제 날짜를 관리합니다.
 * ORDER_PAYMENT_DETAIL
 */
@Data
public class OrderPaymentDetail implements Serializable {
    /**
     * 주문 결제 상세 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 주문 결제 상세 고유 ID (기본 키)
     */
    private Long orderPaymentMainId;

    /**
     * 결제 방법 코드 ID
     */
    private Long paymentMethodCodeId;

    /**
     * 주문 결제 상태 코드 ID
     */
    private Long orderPaymentStatusCodeId;

    /**
     * 승인 번호
     */
    private String approvalNumber;

    /**
     * 결제 금액
     */
    private Integer paymentAmount;

    /**
     * 결제 날짜 및 시간
     */
    private LocalDateTime paymentDate;

    /**
     * 결제에 대한 추가 설명
     */
    private String paymentComment;

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
}