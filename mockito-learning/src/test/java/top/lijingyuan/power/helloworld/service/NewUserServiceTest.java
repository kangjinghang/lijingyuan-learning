package top.lijingyuan.power.helloworld.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import top.lijingyuan.power.common.User;
import top.lijingyuan.power.helloworld.dao.UserDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * NewUserServiceTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = UserService.class)
public class NewUserServiceTest {

    @Test
    public void queryUserCount() throws Exception {
        try {
            UserService userService = new UserService();
            UserDao userDao = mock(UserDao.class);
            whenNew(UserDao.class).withNoArguments().thenReturn(userDao);
            doReturn(10).when(userDao).getCount();
            int result = userService.queryUserCount();
            assertEquals(10, result);
        } catch (Exception e) {
            fail("should not process to here");
        }
    }

    @Test
    public void saveUser() throws Exception {
        try {
            UserService userService = new UserService();
            UserDao userDao = mock(UserDao.class);
            whenNew(UserDao.class).withNoArguments().thenReturn(userDao);
            User user = new User();
            doNothing().when(userDao).insertUser(user);
            userService.saveUser(user);
            Mockito.verify(userDao, Mockito.times(1)).insertUser(user);
        } catch (Exception e) {
            fail("should not process to here");
        }
    }

}