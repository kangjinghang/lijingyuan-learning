package top.lijingyuan.power.mockstatic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import top.lijingyuan.power.common.User;
import top.lijingyuan.power.mockstatic.dao.UserDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * UserServiceTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {UserService.class, UserDao.class})
public class UserServiceTest {

    @Test
    public void queryUserCount() {
        try {
            mockStatic(UserDao.class);
            when(UserDao.getCount()).thenReturn(10);
            UserService userService = new UserService();
            int result = userService.queryUserCount();
            assertEquals(10, result);
        } catch (Exception e) {
            fail("should not process to here");
        }
    }

    @Test
    public void saveUser() {
        try {
            mockStatic(UserDao.class);
            User user = new User();
            doNothing().when(UserDao.class);

            UserService userService = new UserService();
            userService.saveUser(user);
            PowerMockito.verifyStatic(Mockito.times(1));
        } catch (Exception e) {
            fail("should not process to here");
        }
    }
}