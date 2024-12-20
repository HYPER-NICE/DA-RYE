package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * POINT_HISTORY
 */
@Data
public class PointTransaction implements Serializable {
    /**
     * 포인트 발생 ID
     */
    private Long id;

    /**
     * 회원 ID (외래 키)
     */
    private Long memberId;

    /**
     * 포인트 거래 유형 ID (외래 키)
     */
    private Long pointTransactionTypeId;

    /**
     * 주문 ID
     */
    private Long orderMainId;

    /**
     * 포인트 증감량(양수: 적립, 음수: 차감)
     */
    private Integer amount;

    /**
     * 거래 설명
     */
    private String description;

    /**
     * 생성 날짜
     */
    private Date createdDate;

    /**
     * 수정 날짜
     */
    private Date lastModifiedDate;

    /**
     * 삭제 날짜
     */
    private Date deletedDate;

    private static final long serialVersionUID = 1L;
}