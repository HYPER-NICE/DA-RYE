package hyper.darye.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BOARD
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board implements Serializable {
    /**
     * 게시판 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 부모 게시판 ID (외래 키)
     */
    private Long parentId;

    /**
     * 게시판 카테고리 ID (외래 키)
     */
    private Long categoryId;

    /**
     * 작성자 ID (외래 키)
     */
    private Long writerId;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 내용
     */
    private String content;

    /**
     * 게시글 상단 고정 여부
     */
    private Boolean fixed;

    /**
     * 게시글 작성 날짜
     */
    private Date regDate;

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