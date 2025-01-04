package hyper.darye.model.dto.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignIn {

    @Schema(defaultValue = "root@darye.dev")
    @NotBlank
    private String email;

    @Schema(defaultValue = "root")
    @NotBlank
    private String password;
}
