package hyper.darye.dto.controller.response;

import hyper.darye.dto.BoardImage;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchBoardDetailDTO {

    private Long id;
    private String title;
    private String content;
    private Long subCategoryId;
    private String subCategoryName;
    private Date regDate;
    private Date lastModifiedDate;
    private List<BoardImage> images;

}
