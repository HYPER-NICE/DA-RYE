package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 공통 코드 테이블로, 다양한 유형의 코드를 관리하기 위한 테이블입니다. POINT_TRANSACTION_TYPE, ORDER_STATUS 등 다양한 코드 유형을 저장할 수 있습니다.
 * POINT_TRANSACTION_TYPE
 */
@Data
public class PointTransactionType implements Serializable {
    /**
     * 공통 코드 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 코드 유형(예: POINT_TRANSACTION_TYPE, ORDER_STATUS 등)
     */
    private String codeType;

    /**
     * 코드 값(예: SAVE, USE, CANCEL)
     */
    private String codeValue;

    /**
     * 코드 명칭(예: 포인트 적립, 포인트 사용, 포인트 취소)
     */
    private String name;

    /**
     * 코드에 대한 상세 설명
     */
    private String description;

    /**
     * 레코드 생성 날짜 및 시간
     */
    private Date createdDate;

    /**
     * 마지막 수정 날짜 및 시간
     */
    private Date lastModifiedDate;

    /**
     * 레코드가 논리적 삭제된 날짜
     */
    private Date deletedDate;

    private static final long serialVersionUID = 1L;
}