package hyper.darye.system.validation.email;

import hyper.darye.mvc.service.MemberService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final MemberService memberService;

    @Autowired
    public UniqueEmailValidator(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // 이메일이 비어 있으면 다른 검증으로 처리하므로 true 반환
        if (email == null || email.isEmpty()) {
            return true;
        }

        // 이메일 중복 여부 확인
        return !memberService.isEmailTaken(email);
    }
}
