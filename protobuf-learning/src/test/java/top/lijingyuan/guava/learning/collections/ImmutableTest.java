package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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


    @Test
    public void testJDKImmutable() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        System.out.println(list);

        List<String> unmodifiableList = Collections.unmodifiableList(list);

        System.out.println(unmodifiableList);

        List<String> unmodifiableList1 = Collections.unmodifiableList(Arrays.asList("a", "b", "c"));
        System.out.println(unmodifiableList1);

        String temp = unmodifiableList.get(1);
        System.out.println("unmodifiableList [0]：" + temp);

        list.add("baby");
        System.out.println("list add a item after list:" + list);
        System.out.println("list add a item after unmodifiableList:" + unmodifiableList);

        unmodifiableList1.add("bb");
        System.out.println("unmodifiableList add a item after list:" + unmodifiableList1);

        unmodifiableList.add("cc");
        System.out.println("unmodifiableList add a item after list:" + unmodifiableList);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Color {
        int r;
        int g;
        int b;
    }

    @Test
    public void testGuavaImmutable() {

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println("list：" + list);

        ImmutableList<String> imlist = ImmutableList.copyOf(list);
        System.out.println("imlist：" + imlist);

        ImmutableList<String> imOflist = ImmutableList.of("peida", "jerry", "harry");
        System.out.println("imOflist：" + imOflist);

        ImmutableSortedSet<String> imSortSet = ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");
        System.out.println("imSortSet：" + imSortSet);

        list.add("baby");
        System.out.println("list add a item after list:" + list);
        System.out.println("list add a item after imlist:" + imlist);

        ImmutableSet<ImmutableTest.Color> imColorSet =
                ImmutableSet.<ImmutableTest.Color>builder()
                        .add(new ImmutableTest.Color(0, 255, 255))
                        .add(new ImmutableTest.Color(0, 191, 255))
                        .build();

        System.out.println("imColorSet:" + imColorSet);
    }

    @Test
    public void testCotyOf() {
        ImmutableSet<String> imSet = ImmutableSet.of("peida", "jerry", "harry", "lisa");
        System.out.println("imSet：" + imSet);
        ImmutableList<String> imlist = ImmutableList.copyOf(imSet);
        System.out.println("imlist：" + imlist);
        ImmutableSortedSet<String> imSortSet = ImmutableSortedSet.copyOf(imSet);
        System.out.println("imSortSet：" + imSortSet);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "x");
        }
        System.out.println("list：" + list);
        ImmutableList<String> imInfolist = ImmutableList.copyOf(list.subList(2, 18));
        System.out.println("imInfolist：" + imInfolist);
        int imInfolistSize = imInfolist.size();
        System.out.println("imInfolistSize：" + imInfolistSize);
        ImmutableSet<String> imInfoSet = ImmutableSet.copyOf(imInfolist.subList(2, imInfolistSize - 3));
        System.out.println("imInfoSet：" + imInfoSet);
    }

    @Test
    public void testAsList() {
        ImmutableList<String> imList = ImmutableList.of("peida", "jerry", "harry", "lisa", "jerry");
        System.out.println("imList：" + imList);
        ImmutableSortedSet<String> imSortList = ImmutableSortedSet.copyOf(imList);
        System.out.println("imSortList：" + imSortList);
        System.out.println("imSortList as list：" + imSortList.asList());
    }

}
