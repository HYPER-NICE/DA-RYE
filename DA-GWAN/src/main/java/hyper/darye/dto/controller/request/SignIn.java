package hyper.darye.dto.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignIn {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
