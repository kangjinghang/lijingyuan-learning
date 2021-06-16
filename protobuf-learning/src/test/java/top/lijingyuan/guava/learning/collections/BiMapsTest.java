package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * BiMapsTest(键、值都是唯一的)
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class BiMapsTest {

    @Test
    public void testCreate() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "2");
        biMap.put("1", "3");
        biMap.forcePut("2", "2");
        assertThat(biMap.containsKey("1"), equalTo(true));

        try {
            biMap.put("2", "2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInverse() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "2");
        biMap.put("2", "3");
        biMap.put("3", "4");
        BiMap<String, String> inverse = biMap.inverse();
        System.out.println(inverse);
    }

}
