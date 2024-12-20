package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 상품의 재고 정보 테이블
 * STOCK
 */
@Data
public class Stock implements Serializable {
    /**
     * 재고 ID (기본 키)
     */
    private Long id;

    /**
     * 제품 ID (외래 키)
     */
    private Long productId;

    /**
     * 입출고 수량(입고는 +, 출고는 -)
     */
    private Long stockInoutQuantity;

    /**
     * 입출고 사유(입고는 +, 출고는 -)
     */
    private String stockChangeNote;

    /**
     * 입출고 날짜
     */
    private Date stockInoutDate;

    /**
     * 현재 재고 수량(입출고가 발생한 뒤 계산된 값)
     */
    private Long currentStock;

    /**
     * 생성 날짜
     */
    private Date createdDate;

    /**
     * 수정 날짜
     */
    private Date lastModifiedDate;

    /**
     * 레코드를 마지막으로 수정한 회원 ID
     */
    private Long lastModifiedMember;

    /**
     * 삭제 날짜
     */
    private Date deletedDate;

    private static final long serialVersionUID = 1L;
}