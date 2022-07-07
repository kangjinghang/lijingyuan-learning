package top.lijingyuan.mockito.learing.lesson03;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import top.lijingyuan.mockito.learing.common.Account;
import top.lijingyuan.mockito.learing.common.AccountDao;

import static org.mockito.Mockito.mock;

/**
 * MockByRunnerTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-27
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class MockByRunnerTest {

    @Test
    public void testMock() {
        AccountDao accountDao = mock(AccountDao.class, Mockito.RETURNS_SMART_NULLS);
        Account account = accountDao.findAccount("x", "x");
        System.out.println(account);
    }

}
