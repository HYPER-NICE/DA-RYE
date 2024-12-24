package hyper.darye.constant;

import lombok.Getter;

@Getter
public enum RootCategory {
    NOTICE(1L),
    FAQ(2L),
    ONE_ON_ONE(3L);

    private final Long value;

    RootCategory(Long value) {
        this.value = value;
    }
}
