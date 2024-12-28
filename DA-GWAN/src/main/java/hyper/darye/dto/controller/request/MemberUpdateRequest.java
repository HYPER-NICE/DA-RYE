package hyper.darye.dto.controller.request;

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
    private Date birthdate;
    private String contact;
}
