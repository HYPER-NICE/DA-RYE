package hyper.darye.validation.FieldCompare;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldComparisonValidator implements ConstraintValidator<FieldComparison, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 객체가 null인 경우, 다른 필드 레벨의 검증(@NotBlank 등)에 위임
        if (value == null) {
            return true;
        }

        try {
            Class<?> clazz = value.getClass();
            Field targetField = null;
            Field resultField = null;

            // 객체의 모든 필드를 순회하며 애너테이션 확인
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(CompareTarget.class)) {
                    targetField = field;
                }
                if (field.isAnnotationPresent(CompareResult.class)) {
                    resultField = field;
                }
            }

            if (targetField == null || resultField == null) {
                // 필수 필드가 누락된 경우, 검증을 통과하도록 설정 (필요 시 변경 가능)
                return true;
            }

            targetField.setAccessible(true);
            resultField.setAccessible(true);

            Object targetValue = targetField.get(value);
            Object resultValue = resultField.get(value);

            String targetFieldName = targetField.getName();
            String resultFieldName = resultField.getName();

            // 비교 1: 두 필드가 모두 null이면 검증 통과
            if (targetValue == null && resultValue == null) {
                return true;
            }

            // 비교 2: 둘 중 하나만 null이면 검증 실패
            if (targetValue == null || resultValue == null) {
//                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                String.format("'%s' 필드와 '%s' 필드가 같지 않습니다.", targetFieldName, resultFieldName)
                        ).addPropertyNode(resultFieldName)
                        .addConstraintViolation();
                return false;
            }

            // 비교 3: 두 값이 같지 않으면 검증 실패
            if (!targetValue.equals(resultValue)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                String.format("'%s' 필드와 '%s' 필드가 같지 않습니다.", targetFieldName, resultFieldName)
                        ).addPropertyNode(resultFieldName)
                        .addConstraintViolation();
                return false;
            }

            // 검증 성공
            return true;

        } catch (IllegalAccessException e) {
            // Reflection 관련 예외 처리
            throw new RuntimeException("Reflection 오류: 필드 접근에 실패했습니다.", e);
        }
    }
}
