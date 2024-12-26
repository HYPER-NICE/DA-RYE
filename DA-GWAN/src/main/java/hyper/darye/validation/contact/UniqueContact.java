package hyper.darye.validation.contact;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueContactValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueContact {
    String message() default "이미 등록된 휴대폰 번호입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
