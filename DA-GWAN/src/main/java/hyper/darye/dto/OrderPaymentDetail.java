package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 주문의 결제 상세 정보를 저장하는 테이블로, 각 주문의 결제 금액과 결제 날짜를 관리합니다.
 * ORDER_PAYMENT_DETAIL
 */
@Data
public class OrderPaymentDetail extends OrderPaymentDetailKey implements Serializable {
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
    private Date paymentDate;

    /**
     * 결제에 대한 추가 설명
     */
    private String paymentComment;

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