package top.lijingyuan.junit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * JUnit4ParameterTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-11-07
 * @since 1.0.0
 */
@RunWith(Parameterized.class)
public class JUnit4ParameterTest {

    @Parameterized.Parameter(0)
    public String literal;

    @Parameterized.Parameter(1)
    public int length;

    @Parameterized.Parameters(name = "{index} <==> literal={0} length = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[]{"JUnit", 5}, new Object[]{"Java", 4}, new Object[]{"Programming", 11});
    }

    @Test
    public void theLiteralLengthShouldCorrect() {
        Assert.assertEquals(length, literal.length());
    }

}
