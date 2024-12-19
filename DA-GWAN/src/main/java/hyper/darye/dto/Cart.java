package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CART
 */
@Data
public class Cart implements Serializable {
    /**
     * 장바구니 ID (기본 키)
     */
    private Long id;

    /**
     * 회원 ID (외래 키)
     */
    private Long memberId;

    /**
     * 상품 ID (외래 키)
     */
    private Long productId;

    /**
     * 상품별 주문 수량
     */
    private Long quantity;

    /**
     * 장바구니에 담은 날짜
     */
    private Date pickedDate;

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