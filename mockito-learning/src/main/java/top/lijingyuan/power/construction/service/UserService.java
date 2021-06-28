package top.lijingyuan.power.construction.service;

import top.lijingyuan.power.construction.dao.UserDao;

/**
 * UserService
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
public class UserService {

    public void save(String username, String password) {
        UserDao userDao = new UserDao(username, password);
        userDao.insert();
    }

}
