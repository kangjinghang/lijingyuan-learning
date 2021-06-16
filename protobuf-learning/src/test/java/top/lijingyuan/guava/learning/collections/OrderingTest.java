package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * OrderingTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class OrderingTest {

    @Test
    public void testJDKOrder() {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
    }

    @Test(expected = NullPointerException.class)
    public void testJDKOrderIssue() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
    }

    @Test
    public void testOrderingNaturalByNullFirst() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        System.out.println(list);
        Collections.sort(list, Ordering.natural().nullsFirst());
        System.out.println(list);
    }

    @Test
    public void testOrderingNaturalByNullLast() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        System.out.println(list);
        Collections.sort(list, Ordering.natural().nullsLast());
        System.out.println(list);
    }

    @Test
    public void testOrderingNatural() {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        Collections.sort(list);
        assertThat(Ordering.natural().isOrdered(list), equalTo(true));
    }

    @Test
    public void testOrderReserve() {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        Collections.sort(list,Ordering.natural().reverse());
        System.out.println(list);
    }

}
