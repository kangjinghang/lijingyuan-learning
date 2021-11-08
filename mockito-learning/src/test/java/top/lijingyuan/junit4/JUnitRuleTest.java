package top.lijingyuan.junit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * JUnitRuleTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-11-07
 * @since 1.0.0
 */
@RunWith(Theories.class)
public class JUnitRuleTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public List<String> list;

    @Test
    public void shouldAddElement2ListCorrect() {
        when(list.add("Java")).thenReturn(true);
        when(list.size()).thenReturn(10);
        assertEquals(10, list.size());
        assertTrue(list.add("Java"));
    }

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
