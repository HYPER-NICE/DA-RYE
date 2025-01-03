package hyper.darye.mvc.model.dto.controller.response;

import hyper.darye.mvc.model.entity.BoardImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchReplyDTO {

    private Long id;
    private Long parentId;
    private String content;
    private List<BoardImage> images;
    private Date regDate;

}
