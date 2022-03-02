package _05;

public enum AccountType {
    FREE("Free"),
    TRAIL("Trail"),
    SILVER("Silver"),
    GOLD("Gold");

    private final String value;

    AccountType(String name){
        this.value = name;
    }

}
