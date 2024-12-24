package hyper.darye.dto.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
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
    @NotNull(message = "카테고리를 선택해주세요.")
    private Long subCategory;

    private Long rootCategory;

    private List<byte[]> images;

}
