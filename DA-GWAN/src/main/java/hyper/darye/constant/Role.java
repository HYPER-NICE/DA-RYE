package hyper.darye.constant;

/**
 * 데이터 베이스에서 가져와야할것 같은데...?
 */
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
