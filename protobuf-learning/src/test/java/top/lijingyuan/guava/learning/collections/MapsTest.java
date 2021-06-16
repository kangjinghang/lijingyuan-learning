package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * MapsTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class MapsTest {

    @Test
    public void testCreate() {
        ArrayList<String> valueList = Lists.newArrayList("1", "2", "3");
        ImmutableMap<String, String> map = Maps.uniqueIndex(valueList, value -> value + "_key");
        System.out.println(map);

        Map<String, String> map2 = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        System.out.println(map2);
    }

    @Test
    public void testTransform() {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        System.out.println(map);
        Map<String, String> newMap = Maps.transformValues(map, v -> v + "_transform");
        assertThat(newMap.containsValue("1_value_transform"), equalTo(true));
    }

    @Test
    public void testFilter() {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        System.out.println(map);
        Map<String, String> result = Maps.filterKeys(map, k -> Lists.newArrayList("1", "2").contains(k));
        assertThat(result.containsKey("3"), equalTo(false));
    }

}
