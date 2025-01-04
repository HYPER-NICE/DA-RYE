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
public class PostReplyDTO {

    @Schema(defaultValue = "내용")
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    private Long parentId;

    private List<byte[]> images;
}
