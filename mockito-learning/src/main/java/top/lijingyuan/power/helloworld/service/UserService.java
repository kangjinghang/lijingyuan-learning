package top.lijingyuan.power.helloworld.service;

import top.lijingyuan.power.common.User;
import top.lijingyuan.power.helloworld.dao.UserDao;

/**
 * UserService
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
public class UserService {

    public UserService() {
    }

    public UserService(UserDao userDao) {
//        this.userDao = userDao;
    }

    public int queryUserCount(){
        UserDao userDao = new UserDao();
        return userDao.getCount();
    }

    public void saveUser(User user){
        UserDao userDao = new UserDao();
        userDao.insertUser(user);
    }

}
