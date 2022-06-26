package top.lijingyuan.springboot.test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubAccount {

    private long id;

    private volatile int balanceAmount;

    private volatile int frozenAmount;

    public SubAccount(long id, int balanceAmount) {
        this.id = id;
        this.balanceAmount = balanceAmount;
    }
}
