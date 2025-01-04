package hyper.darye.model.dto.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReplyDTO {

    private String content;

    //추가할 이미지 파일
    private List<byte[]> images;

    //삭제할 이미지 파일
    private Long[] deleteImages;
}
