package top.lijingyuan.mockito.learing.lesson08;

import java.io.Serializable;
import java.util.Collection;

/**
 * SimpleService
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-27
 * @since 1.0.0
 */
public class SimpleService {

    public int method1(int i, String s, Collection<?> c, Serializable ser) {
        throw new RuntimeException();
    }

    public void method2(int i, String s, Collection<?> c, Serializable ser) {
        throw new RuntimeException();
    }
}
