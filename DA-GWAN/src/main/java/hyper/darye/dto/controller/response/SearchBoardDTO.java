package hyper.darye.dto.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 게시판 검색 결과를 표현하는 DTO 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBoardDTO {

    /**
     * 게시판 ID (고유 식별자)
     */
    @JsonProperty("id")
    private Long id;

    /**
     * 게시판 제목
     */
    @JsonProperty("title")
    private String title;

    /**
     * 하위 카테고리 ID
     */
    @JsonProperty("subCategoryId")
    private Long subCategoryId;

    /**
     * 하위 카테고리 이름
     */
    @JsonProperty("subCategoryName")
    private String subCategoryName;

    /**
     * 게시판 등록 날짜 (ISO 8601 형식, 예: "2024-12-28T06:33:19")
     */
    @JsonProperty("regDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date regDate;
}
