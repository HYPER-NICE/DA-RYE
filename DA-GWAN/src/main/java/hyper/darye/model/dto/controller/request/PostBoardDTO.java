package hyper.darye.model.dto.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBoardDTO {

    @Schema(defaultValue = "제목")
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @Schema(defaultValue = "내용")
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @Schema(defaultValue = "false")
    private Boolean fixed;

    @Schema(defaultValue = "2")
//    @NotNull(message = "카테고리를 선택해주세요.") 하...1대1 문의는 자동으로 들어가게 하는게 맞아서 일단 필수에서 뺐슴니다...DTO 따로 만드는게 좋아보이는데 일단은...
    private Long subCategory;

    private Long rootCategory;

    private List<byte[]> images;
}
