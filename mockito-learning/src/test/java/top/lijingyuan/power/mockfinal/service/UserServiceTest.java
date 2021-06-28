package top.lijingyuan.power.mockfinal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import top.lijingyuan.power.mockfinal.dao.UserDao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * UserServiceTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {UserService.class, UserDao.class})
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Test
    public void queryUserCountWithMock() {
        MockitoAnnotations.initMocks(this);
        UserService userService = new UserService(userDao);
        when(userDao.getCount()).thenReturn(10);
        int result = userService.queryUserCount();
        assertEquals(10, result);
    }

    @Test
    public void queryUserCountWithPowerMock() {
        UserDao uDao = PowerMockito.mock(UserDao.class);
        System.out.println(uDao.getClass());
        UserService userService = new UserService(uDao);
        when(uDao.getCount()).thenReturn(10);
        int result = userService.queryUserCount();
        assertEquals(10, result);
    }

    @Test
    public void saveUser() {
    }
}