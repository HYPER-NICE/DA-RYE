package hyper.darye.dto;

import hyper.darye.validation.FieldCompare.FieldComparison;
import hyper.darye.validation.FieldCompare.CompareResult;
import hyper.darye.validation.FieldCompare.CompareTarget;
import hyper.darye.validation.contact.UniqueContact;
import hyper.darye.validation.email.UniqueEmail;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 가입 요청 DTO - 필수 및 선택 정보 포함
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldComparison(message = "패스워드와 확인 패스워드가 일치하지 않습니다.")
@Schema(description = "회원 가입 요청 DTO - 필수 및 선택 정보 포함")
public class SignUp {

    /** 필수 정보 **/

    /**
     * 이름 (필수)
     */
    @Schema(description = "회원 이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    /**
     * 이메일 (필수)
     */
    @Schema(description = "회원 이메일 주소", example = "example@darye.dev", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일이 정확하지 않습니다.")
    @UniqueEmail(message = "이미 등록된 이메일입니다.")
    private String email;

    /**
     * 비밀번호 (필수)
     * - 최소 8자, 대문자, 소문자, 숫자, 특수문자 포함
     */
    @Schema(description = "회원 비밀번호", example = "Password@123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "암호를 입력하세요.")
    @Size(min = 4, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
    @CompareTarget
    private String password;

    /**
     * 비밀번호 확인 (필수)
     */
    @Schema(description = "비밀번호 확인", example = "Password@123", requiredMode = Schema.RequiredMode.REQUIRED)
    @CompareResult
    private String confirmPassword;

    /**
     * 연락처 (필수)
     * - 한국 표준 휴대폰 번호 패턴 적용
     */
    @Schema(description = "회원 연락처 (010-1234-5678 형식)", example = "010-1234-5678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "연락처를 입력하세요.")
    @Pattern(
            regexp = "^01[0-9]-\\d{3,4}-\\d{4}$",
            message = "연락처 형식이 올바르지 않습니다. (예: 010-1234-5678)"
    )
    @UniqueContact(message = "이미 등록된 휴대폰 번호입니다.")
    private String contact;
}
