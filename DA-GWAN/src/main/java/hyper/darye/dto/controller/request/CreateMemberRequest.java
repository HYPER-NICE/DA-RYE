package hyper.darye.dto.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberRequest {
    private Long id;
    private String email;
    private String password;
    private String rePassword;
    private String name;
    private Character sex;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private String contact;

    public void setSex(Character sex) {
        if (sex != 'M' && sex!= 'F' && sex!= 'O')
            throw new IllegalArgumentException("잘못된 값입니다. 생물학적 성별에는 M(남자), F(여자), O(기타)만 입력됩니다.");
        this.sex = sex;
    }

}
