package hyper.darye.mvc.model.dto.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemberUpdateRequest {
    //  지금은 이메일 변경 불가능
//    private String email;
    private String name;
    private Character sex;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date birthdate;
    private String contact;
}
