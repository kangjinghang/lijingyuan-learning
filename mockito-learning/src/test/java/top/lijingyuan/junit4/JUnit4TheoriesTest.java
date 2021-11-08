package top.lijingyuan.junit4;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * JUnit4TheoriesTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-11-07
 * @since 1.0.0
 */
@RunWith(Theories.class)
public class JUnit4TheoriesTest {

    @DataPoints
    public static int[] data() {
        return new int[]{1, 10, 100};
    }

    @Theory
    public void sumTwoNumericAddShouldGreatThanTheOne(int a, int b) {
        Assert.assertTrue(a + b > a);
        System.out.printf("%d+%d>%d\n", a, b, a);
    }

}

