package hyper.darye.validation;

import hyper.darye.dto.SignUp;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 비밀번호 확인 검증 로직
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, SignUp> {

    @Override
    public boolean isValid(SignUp signUp, ConstraintValidatorContext context) {
        if (signUp == null || signUp.getPassword() == null || signUp.getConfirmPassword() == null) {
            return false;
        }

        boolean isValid = signUp.getPassword().equals(signUp.getConfirmPassword());

        if (!isValid) {
            // 기본 메시지 비활성화
            context.disableDefaultConstraintViolation();

            // 사용자 정의 메시지 추가
            context.buildConstraintViolationWithTemplate("비밀번호가 일치하지 않습니다.")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
