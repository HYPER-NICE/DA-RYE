package hyper.darye.mvc.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 게시판 유형 코드 테이블로, 다양한 게시판 유형을 관리하기 위한 테이블입니다.
 * BOARD_CATEGORY_CODE
 */
@Data
public class BoardCategoryCode implements Serializable {
    /**
     * 게시판 카테고리 코드 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 부모 카테고리 코드
     */
    private Long rootCategory;

    /**
     * 서브 카테고리 코드
     */
    private Long subCategory;

    /**
     * 루트 카테고리 명칭(예: 공지사항, FAQ, 1대1 문의)
     */
    private String rootName;

    /**
     * 루트 카테고리에 대한 상세 설명
     */
    private String rootDescription;

    /**
     * 서브 카테고리 명칭(예: 전체, 배송, 교환, 환불)
     */
    private String subName;

    /**
     * 서브 카테고리에 대한 상세 설명
     */
    private String subDescription;

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