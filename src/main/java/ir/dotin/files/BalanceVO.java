package ir.dotin.files;

import java.io.Serializable;
import java.math.BigDecimal;

public class BalanceVO implements Serializable {

    String depositNumber;
    BigDecimal amount;

    public BalanceVO() {
    }

    public BalanceVO(String depositNumber, BigDecimal amount) {
        this.depositNumber = depositNumber;
        this.amount = amount;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return depositNumber + "\t" + amount;
    }

}
