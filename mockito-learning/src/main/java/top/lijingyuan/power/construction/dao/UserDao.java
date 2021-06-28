package top.lijingyuan.power.construction.dao;

/**
 * UserDao
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-28
 * @since 1.0.0
 */
public class UserDao {

    private String username;

    private String password;

    public UserDao(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void insert(){
        throw new UnsupportedOperationException();
    }

}
