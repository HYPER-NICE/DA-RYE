package hyper.darye.mvc.model.dto.controller.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}