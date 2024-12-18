package hyper.darye.dto.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateMemberRequest {
    private Long id;
    private String email;
    private String password;
    private String rePassword;
    private String name;
    private Character sex;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private String mobile;

    public CreateMemberRequest(String email, String password, String rePassword, String username, Character sex, Date birthdate, String mobile) {
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
        this.name = username;
        this.sex = sex;
        this.birthdate = birthdate;
        this.mobile = mobile;
    }

    public void setSex(Character sex) {
        if (sex != 'M' && sex!= 'F' && sex!= 'O')
            throw new IllegalArgumentException("잘못된 값입니다. 생물학적 성별에는 M(남자), F(여자), O(기타)만 입력됩니다.");
        this.sex = sex;
    }

}
