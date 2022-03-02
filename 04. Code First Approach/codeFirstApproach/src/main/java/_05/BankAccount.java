package _05;

import javax.persistence.*;

@Entity(name="bank_accounts")
public class BankAccount extends BillingDetail{
    @Column(name="bank_name", length = 100)
    private String bankName;

    @Column(name="swift_code", nullable = false, unique = true)
    private String SWIFTCode;

    public BankAccount() {
        super();
    }

    public BankAccount(String number, User owner, String bankName, String SWIFTCode) {
        super(number, owner);
        this.bankName = bankName;
        this.SWIFTCode = SWIFTCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSWIFTCode() {
        return SWIFTCode;
    }

    public void setSWIFTCode(String SWIFTCode) {
        this.SWIFTCode = SWIFTCode;
    }
}
