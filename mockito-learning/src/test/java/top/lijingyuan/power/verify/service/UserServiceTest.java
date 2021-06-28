package top.lijingyuan.power.verify.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import top.lijingyuan.power.common.User;
import top.lijingyuan.power.verify.dao.UserDao;

import static org.junit.Assert.*;

/**
 * UserServiceTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-29
 * @since 1.0.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {UserService.class})
public class UserServiceTest {

    @Test
    public void saveOrUpdate() throws Exception {
        User user = PowerMockito.mock(User.class);
        UserDao userDao = PowerMockito.mock(UserDao.class);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        PowerMockito.when(userDao.getCount(user)).thenReturn(0);
        UserService userService = new UserService();
        userService.saveOrUpdate(user);
        Mockito.verify(userDao).insertUser(user);
        Mockito.verify(userDao, Mockito.never()).updateUser(user);
    }


    @Test
    public void saveOrUpdateWillUseUpdateOriginal() throws Exception {
        User user = PowerMockito.mock(User.class);
        UserDao userDao = PowerMockito.mock(UserDao.class);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        PowerMockito.when(userDao.getCount(user)).thenReturn(1);
        UserService userService = new UserService();
        userService.saveOrUpdate(user);
        Mockito.verify(userDao, Mockito.never()).insertUser(user);
        Mockito.verify(userDao).updateUser(user);
    }

}