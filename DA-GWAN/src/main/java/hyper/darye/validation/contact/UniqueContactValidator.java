package hyper.darye.validation.contact;

import hyper.darye.service.MemberService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueContactValidator implements ConstraintValidator<UniqueContact, String> {

    private final MemberService memberService;

    @Autowired
    public UniqueContactValidator(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean isValid(String contact, ConstraintValidatorContext context) {
        if (contact == null || contact.isEmpty()) {
            return true;
        }

        return !memberService.isContactTaken(contact);
    }
}
