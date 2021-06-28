package top.lijingyuan.power.mockfinal.service;

import top.lijingyuan.power.common.User;
import top.lijingyuan.power.mockfinal.dao.UserDao;

/**
 * UserService
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
final public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public int queryUserCount() {
        return userDao.getCount();
    }

    public void saveUser(User user) {
        userDao.insertUser(user);
    }

}
