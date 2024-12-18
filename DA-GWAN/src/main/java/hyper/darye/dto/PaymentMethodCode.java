package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * PAYMENT_METHOD_CODE
 */
@Data
public class PaymentMethodCode implements Serializable {
    /**
     * 결제 코드 ID (기본 키)
     */
    private Long id;

    /**
     * 결제 코드 이름
     */
    private String name;

    /**
     * 결제 코드 설명
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