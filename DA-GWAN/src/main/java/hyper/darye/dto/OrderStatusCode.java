package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 주문 상태 코드를 저장하며, 주문의 현재 상태를 관리하는 테이블입니다. 예를 들어 결제 완료, 배송 준비 등의 상태를 포함합니다.
 * ORDER_STATUS_CODE
 */
@Data
public class OrderStatusCode implements Serializable {
    /**
     * 주문 상태 코드의 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 주문 상태 이름 (예: 결제 완료, 배송 준비 등)
     */
    private String name;

    /**
     * 주문 상태 코드에 대한 상세 설명으로, 상태의 의미와 용도를 설명합니다.
     */
    private String description;

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