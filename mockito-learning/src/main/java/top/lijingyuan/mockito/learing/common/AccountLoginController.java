package top.lijingyuan.mockito.learing.common;

import javax.servlet.http.HttpServletRequest;

/**
 * AccountLoginController
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-26
 * @since 1.0.0
 */
public class AccountLoginController {

    private final AccountDao accountDao;

    public AccountLoginController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public String login(HttpServletRequest request) {
        final String userName = request.getParameter("username");
        final String password = request.getParameter("password");
        try {
            Account account = accountDao.findAccount(userName, password);
            if (account == null) {
                return "/login";
            } else {
                return "/index";
            }
        } catch (Exception e) {
            return "/505";
        }

    }

}
