package top.lijingyuan.power.mockstatic.service;

import top.lijingyuan.power.common.User;
import top.lijingyuan.power.mockstatic.dao.UserDao;

/**
 * UserService
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
public class UserService {

    public int queryUserCount() {
        return UserDao.getCount();
    }

    public void saveUser(User user) {
        UserDao.insertUser(user);
    }

}
