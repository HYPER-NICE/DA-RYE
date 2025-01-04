package hyper.darye.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CATEGORY
 */
@Data
public class Category implements Serializable {
    /**
     * 카테고리 ID (기본 키)
     */
    private Long id;

    /**
     * 부모 카테고리 ID (외래 키)
     */
    private Long parentId;

    /**
     * 화면에 보여질 정렬 순서
     */
    private Long screenOrder;

    /**
     * 카테고리 이름
     */
    private String name;

    /**
     * 카테고리 설명
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