package _05;

public enum CardType {
    FREE("Free"),
    TRAIL("Trail"),
    SILVER("Silver"),
    GOLD("Gold");

    private final String value;

    CardType(String name){
        this.value = name;
    }

}
