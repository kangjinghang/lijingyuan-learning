package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * MultiMapsTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class MultiMapsTest {

    @Test
    public void testCreate() {
        LinkedListMultimap<String, String> multimap = LinkedListMultimap.create();
        HashMap<String, String> hashMap = Maps.newHashMap();
        hashMap.put("1", "1");
        hashMap.put("1", "2");
        assertThat(hashMap.size(), equalTo(1));

        multimap.put("1", "1");
        multimap.put("1", "2");
        assertThat(multimap.size(), equalTo(2));

        System.out.println(multimap.get("1"));
    }

}
