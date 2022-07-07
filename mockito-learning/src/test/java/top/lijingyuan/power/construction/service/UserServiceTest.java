package top.lijingyuan.power.construction.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import top.lijingyuan.power.construction.dao.UserDao;

/**
 * UserServiceTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-29
 * @since 1.0.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {UserService.class})
public class UserServiceTest {

    @Test
    public void save() throws Exception {
        UserDao userDao = PowerMockito.mock(UserDao.class);
        String username = "kang";
        String password = "123456";

        PowerMockito.whenNew(UserDao.class).withArguments(username, password).thenReturn(userDao);

        UserService userService = new UserService();
        userService.save(username, password);

        Mockito.verify(userDao).insert();
    }
}