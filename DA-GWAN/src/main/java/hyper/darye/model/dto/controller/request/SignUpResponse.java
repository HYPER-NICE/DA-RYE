package hyper.darye.model.dto.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 회원 가입 응답 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse implements Serializable {

    /**
     * 회원 ID
     */
    private Long id;

    /**
     * 회원 이름
     */
    private String name;

    /**
     * 회원 이메일
     */
    private String email;

    /**
     * 연락처 (휴대폰 번호)
     */
    private String contact;

    /**
     * 회원 역할
     */
    private String role;

    /**
     * 가입 날짜
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date regDate;

    /**
     * 계정 활성화 상태
     */
    private Boolean active;

    /**
     * 계정 잠금 여부
     */
    private Boolean locked;

    /**
     * 계정 삭제 여부
     */
    private Boolean deleted;

    private static final long serialVersionUID = 1L;
}
