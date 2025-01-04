package hyper.darye.dto.controller.response;

import hyper.darye.model.entity.BoardImage;
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
