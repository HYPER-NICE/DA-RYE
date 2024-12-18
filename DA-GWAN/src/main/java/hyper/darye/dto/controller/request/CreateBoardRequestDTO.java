package hyper.darye.dto.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBoardRequestDTO {

    private Long writerId;
    private String title;
    private String content;
    private Boolean fixed;
    private String subName;

}
