package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 회원의 주문 정보를 저장하는 메인 테이블로, 주문의 기본 정보(총 금액, 주문 날짜 등)를 관리합니다.
 * ORDER_MAIN
 */
@Data
public class OrderMain implements Serializable {
    /**
     * 주문 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 주문을 생성한 회원의 고유 ID (외래 키)
     */
    private Long memberId;

    /**
     * 주문이 발생한 날짜 및 시간
     */
    private Date orderDate;

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