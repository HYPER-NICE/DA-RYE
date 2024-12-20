package hyper.darye.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemberUpdateRequest {
    private String email;
    private String name;
    private Character sex;
    private Date birthdate;
    private String mobile;
}
