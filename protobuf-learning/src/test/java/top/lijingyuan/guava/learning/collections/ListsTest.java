package top.lijingyuan.guava.learning.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * ListsTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class ListsTest {

    @Test
    public void testCartesianProduct() {
        List<List<String>> lists = Lists.cartesianProduct(Lists.newArrayList("1", "2"), Lists.newArrayList("A", "B"));
        System.out.println(lists);
    }

    @Test
    public void testTransform() {
        ArrayList<String> sourceList = Lists.newArrayList("Scala", "Guava", "Lists");
        Lists.transform(sourceList, String::toUpperCase).forEach(System.out::println);
    }

    @Test
    public void testNewArrayListWithCapacity() {
        ArrayList<String> result = Lists.newArrayListWithCapacity(10);
        result.add("x");
        result.add("y");
        result.add("z");
        System.out.println(result);
    }

    @Test
    public void testNewArrayListWithExpectedSize() {
        Lists.newArrayListWithExpectedSize(10);
    }

    @Test
    public void testReverse() {
        ArrayList<String> list = Lists.newArrayList("1", "2", "3");
        assertThat(Joiner.on(",").join(list), equalTo("1,2,3"));

        List<String> reverse = Lists.reverse(list);
        assertThat(Joiner.on(",").join(reverse), equalTo("3,2,1"));
    }

    @Test
    public void testPartition() {
        ArrayList<String> list = Lists.newArrayList("1", "2", "3");
        List<List<String>> result = Lists.partition(list, 2);
        System.out.println(result);
    }

}
