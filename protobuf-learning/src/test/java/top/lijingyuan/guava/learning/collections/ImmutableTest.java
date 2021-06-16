package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * ImmutableTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class ImmutableTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testOtherMethod() {
        Range<Integer> atLeast = Range.atLeast(2);
        System.out.println(atLeast);
        assertThat(atLeast.contains(2), equalTo(true));
        assertThat(atLeast.contains(Integer.MAX_VALUE), equalTo(true));
        System.out.println(Range.open(1, 5));
        System.out.println(Range.lessThan(10));
        System.out.println(Range.atMost(5));
        System.out.println(Range.all());
        System.out.println(Range.downTo(10, BoundType.CLOSED));
        System.out.println(Range.upTo(10, BoundType.CLOSED));
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        list.add(4);
        fail();
    }

    @Test
    public void testCopy() {
        Integer[] array = {1, 2, 3, 4, 5};
        System.out.println(ImmutableList.copyOf(array));
    }

    @Test
    public void testBuilder() {
        ImmutableList<Integer> list = ImmutableList.<Integer>builder().add(1).add(2, 3, 4).addAll(Arrays.asList(5, 6)).build();
        System.out.println(list);
    }

    @Test
    public void testImmutableMap() {
        ImmutableMap<String, String> map = ImmutableMap.of("Java", "1", "Oracle", "2");
        System.out.println(map);
        ImmutableMap<String, String> build = ImmutableMap.<String, String>builder().put("a", "b").build();
        System.out.println(build);
    }

}
