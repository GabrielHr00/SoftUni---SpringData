package _05;

import javax.persistence.*;

@Entity(name = "credit_cards")
public class CreditCard extends BillingDetail{
    @Enumerated(EnumType.STRING)
    private CardType type;

    @Column(name = "expiration_month")
    private String expirationMonth;

    @Column(name = "expiration_year")
    private long expirationYear;

    public CreditCard(){
        super();
    }

    public CreditCard(String number, User owner, CardType type, String expirationMonth, long expirationYear) {
        super(number, owner);
        this.type = type;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public long getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(long expirationYear) {
        this.expirationYear = expirationYear;
    }
}
