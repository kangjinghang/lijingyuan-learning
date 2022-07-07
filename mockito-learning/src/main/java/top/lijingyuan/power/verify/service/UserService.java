package top.lijingyuan.power.verify.service;

import top.lijingyuan.power.common.User;
import top.lijingyuan.power.verify.dao.UserDao;

/**
 * UserService
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
public class UserService {

    public void saveOrUpdate(User user) {
        UserDao userDao = new UserDao();
        if (userDao.getCount(user) > 0) {
            userDao.updateUser(user);
        } else {
            userDao.insertUser(user);
        }
    }

}
