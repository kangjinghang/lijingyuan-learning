package top.lijingyuan.mockito.learing.lesson03;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import top.lijingyuan.mockito.learing.common.Account;
import top.lijingyuan.mockito.learing.common.AccountDao;

import static org.mockito.Mockito.mock;

/**
 * MockByRuleTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-27
 * @since 1.0.0
 */
public class MockByRuleTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AccountDao accountDao;

    @Test
    public void testMock() {
        AccountDao accountDao = mock(AccountDao.class);
        Account account = accountDao.findAccount("x", "x");
        System.out.println(account);
    }

    @Test
    public void testMock1() {
        Account account = accountDao.findAccount("x", "x");
        System.out.println(account);
    }

}
