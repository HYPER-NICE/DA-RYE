package hyper.darye.system.constant;

public enum StockType {
    INCOMING("입고"),
    OUTCOMING("출고"),
    REFUND("환불"),
    DEMAGED("불량"),
    CHECKED("검사");

    private final String description;


    StockType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
