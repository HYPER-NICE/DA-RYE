package hyper.darye.dto.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UpdateBoardDTO {

    private String title;
    private String content;
    private Long subCategory;


    //추가할 이미지 파일
    private List<byte[]> images;

    //삭제할 이미지 파일
    private Long[] deleteImages;


}
