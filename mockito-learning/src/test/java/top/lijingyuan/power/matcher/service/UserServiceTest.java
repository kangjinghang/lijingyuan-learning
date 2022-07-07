package top.lijingyuan.power.matcher.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import top.lijingyuan.power.matcher.dao.UserDao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

/**
 * UserServiceTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-29
 * @since 1.0.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = UserService.class)
public class UserServiceTest {

    @Test
    public void find() throws Exception {
        UserDao userDao = PowerMockito.mock(UserDao.class);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);

        PowerMockito.when(userDao.queryByName("alex")).thenReturn("wangwenjun");
        UserService userService = new UserService();
        String result = userService.find("alex");
        assertEquals("wangwenjun", result);
    }

    @Test
    public void findWithMatcher() throws Exception {
        UserDao userDao = PowerMockito.mock(UserDao.class);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);

        PowerMockito.when(userDao.queryByName(Matchers.argThat(new MyArgumentMatcher()))).thenReturn("wangwenjun");
        UserService userService = new UserService();
        assertEquals("wangwenjun", userService.find("Alex"));
        assertEquals("wangwenjun", userService.find("Jacky"));
        assertEquals("wangwenjun", userService.find("Van"));
        assertEquals("wangwenjun", userService.find("Tony"));
    }

    @Test
    public void findWithAnswer() throws Exception {
        UserDao userDao = PowerMockito.mock(UserDao.class);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);

        PowerMockito.when(userDao.queryByName(anyString())).then((Answer<String>) invocation -> {
            String arg = (String) invocation.getArguments()[0];
            switch (arg) {
                case "Jacky":
                    return "I am Jacky";
                case "Alex":
                    return "I am Alex";
                default:
                    throw new RuntimeException("Not support" + arg);
            }
        });
        UserService userService = new UserService();

        assertEquals("I am Jacky", userService.find("Jacky"));
        assertEquals("I am Alex", userService.find("Alex"));
    }

    static class MyArgumentMatcher extends ArgumentMatcher<String> {

        @Override
        public boolean matches(Object argument) {
            String arg = (String) argument;
            switch (arg) {
                case "Alex":
                case "Jacky":
                case "Van":
                case "Tony":
                    return true;
                default:
                    return false;
            }
        }
    }

}