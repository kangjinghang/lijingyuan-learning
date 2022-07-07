package top.lijingyuan.power.matcher.service;

import top.lijingyuan.power.matcher.dao.UserDao;

/**
 * UserService
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
public class UserService {

    public String find(String name) {
        UserDao userDao = new UserDao();
        return userDao.queryByName(name);
    }

}
