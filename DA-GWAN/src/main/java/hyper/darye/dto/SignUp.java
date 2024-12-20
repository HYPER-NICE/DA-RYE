package hyper.darye.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 가입 요청 DTO - 필수 및 선택 정보 포함
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {

    /** 필수 정보 **/

    /**
     * 이름 (필수)
     */
    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    /**
     * 이메일 (필수)
     */
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일이 정확하지 않습니다.")
    private String email;

    /**
     * 비밀번호 (필수)
     * - 최소 8자, 대문자, 소문자, 숫자, 특수문자 포함
     */
    @NotBlank(message = "암호를 입력하세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;

    /**
     * 비밀번호 확인 (필수)
     */
    @NotBlank(message = "암호를 재입력 하세요.")
    private String confirmPassword;

    /**
     * 연락처 (필수)
     * - 한국 표준 휴대폰 번호 패턴 적용
     */
    @NotBlank(message = "연락처를 입력하세요.")
    @Pattern(
            regexp = "^01[0-9]-\\d{3,4}-\\d{4}$",
            message = "연락처 형식이 올바르지 않습니다. (예: 010-1234-5678)"
    )
    private String contact;

    /** 선택 정보 **/

    /**
     * 권한 (기본값: USER)
     */
    private String role = "USER";
}
