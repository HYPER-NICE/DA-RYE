package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 이미지 테이블로, 게시글에 등록된 이미지를 관리하기 위한 테이블입니다.
 * BOARD_IMAGE
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardImage implements Serializable {
    /**
     * 게시글 이미지 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 게시글 ID (외래 키)
     */
    private Long boardId;

    /**
     * 레코드 생성 날짜 및 시간
     */
    private Date createdDate;

    /**
     * 마지막 수정된 날짜 및 시간
     */
    private Date lastModifiedDate;

    /**
     * 레코드가 삭제된 날짜 (논리 삭제)
     */
    private Date deletedDate;

    /**
     * 게시글 이미지
     */
    private byte[] image;

    private static final long serialVersionUID = 1L;
}