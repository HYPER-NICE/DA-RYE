package hyper.darye.constant;

public enum RootCategory {
    NOTICE(1L),
    FAQ(2L),
    ONE_ON_ONE(3L);

    private final Long value;

    RootCategory(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
