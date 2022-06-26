package top.lijingyuan.springboot.test.repository;

import org.springframework.stereotype.Repository;
import top.lijingyuan.springboot.test.entity.SubAccount;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SubAccountRepository {

    private Map<Long, SubAccount> subAccountMap = new HashMap<>();

    {
        subAccountMap.put(1L, new SubAccount(1, 100));
        subAccountMap.put(2L, new SubAccount(2, 100));
        subAccountMap.put(3L, new SubAccount(3, 100));
    }

    public SubAccount findById(Long id) {
        return subAccountMap.get(id);
    }

    public void save(SubAccount subAccount) {
        subAccountMap.put(subAccount.getId(), subAccount);
    }
}