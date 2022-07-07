package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * SetsTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class SetsTest {

    @Test
    public void testCreate() {
        HashSet<Integer> set = Sets.newHashSet(1, 2, 3);
        assertThat(set.size(), equalTo(3));

        ArrayList<Integer> list = Lists.newArrayList(1, 2, 2, 3);
        assertThat(list.size(), equalTo(4));

        HashSet<Integer> set2 = Sets.newHashSet(list);
        assertThat(set2.size(), equalTo(3));
    }

    @Test
    public void testCartesianProduct() {
        Set<List<Integer>> lists = Sets.cartesianProduct(Sets.newHashSet(1, 2), Sets.newHashSet(3, 4), Sets.newHashSet(5, 6));
        System.out.println(lists);
    }

    @Test
    public void testCombinations() {
        HashSet<Integer> set = Sets.newHashSet(1, 2, 3);
        Set<Set<Integer>> combinations = Sets.combinations(set, 1);
        combinations.forEach(System.out::println);

        combinations = Sets.combinations(set, 2);
        combinations.forEach(System.out::println);
    }

    @Test
    public void testDifference() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 5, 6);
        Sets.SetView<Integer> difference = Sets.difference(set1, set2);
        System.out.println(difference);
        difference = Sets.difference(set2, set1);
        System.out.println(difference);
    }

    @Test
    public void testIntersection() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 5, 6);
        Sets.SetView<Integer> intersection = Sets.intersection(set1, set2);
        System.out.println(intersection);
    }

    @Test
    public void testUnion() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 5, 6);
        Sets.SetView<Integer> union = Sets.union(set1, set2);
        System.out.println(union);
    }

}
