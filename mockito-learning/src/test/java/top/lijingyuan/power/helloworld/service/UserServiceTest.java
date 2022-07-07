package top.lijingyuan.power.helloworld.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import top.lijingyuan.power.common.User;
import top.lijingyuan.power.helloworld.dao.UserDao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * UserServiceTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService(new UserDao());
    }

    @Mock
    private UserDao userDao;

    @Test
    public void queryUserCount() throws Exception {
        try {
            userService.queryUserCount();
            fail("should not process to here");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }
    }

    @Test
    public void saveUser() throws Exception {
        try {
            userService.saveUser(new User());
            fail("should not process to here");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }
    }

    @Test
    @Ignore
    public void queryUserCountWithMockito() {
        MockitoAnnotations.initMocks(this);

        UserService service = new UserService(userDao);

        when(userDao.getCount()).thenReturn(10);

        int result = service.queryUserCount();
        assertEquals(result, 10);
    }

    @Test
    public void queryUserCountWithPowerMock() {
        UserDao uDao = PowerMockito.mock(UserDao.class);
//        when(uDao.getCount()).thenReturn(10);
        doReturn(10).when(uDao).getCount();

        UserService service = new UserService(uDao);

        int result = service.queryUserCount();
        assertEquals(result, 10);
    }

    @Test
    public void saveUserWithPowerMock() {
        UserDao uDao = PowerMockito.mock(UserDao.class);
        User user = new User();
        doNothing().when(uDao).insertUser(user);

        UserService service = new UserService(uDao);

        service.saveUser(user);
        verify(uDao).insertUser(user);
    }

}