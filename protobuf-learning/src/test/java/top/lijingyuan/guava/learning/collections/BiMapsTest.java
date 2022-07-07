package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * BiMapsTest(键、值都是唯一的)
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
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

    @Test
    public void logMapTest() {
        Map<Integer, String> logfileMap = Maps.newHashMap();
        logfileMap.put(1, "a.log");
        logfileMap.put(2, "b.log");
        logfileMap.put(3, "c.log");
        System.out.println("logfileMap:" + logfileMap);
    }

    /**
     * 逆转Map的key和value
     *
     * @param <S>
     * @param <T>
     * @param map
     * @return
     */
    public static <S, T> Map<T, S> getInverseMap(Map<S, T> map) {
        Map<T, S> inverseMap = new HashMap<T, S>();
        for (Map.Entry<S, T> entry : map.entrySet()) {
            inverseMap.put(entry.getValue(), entry.getKey());
        }
        return inverseMap;
    }

    @Test
    public void logMapTest2() {
        Map<Integer, String> logfileMap = Maps.newHashMap();
        logfileMap.put(1, "a.log");
        logfileMap.put(2, "b.log");
        logfileMap.put(3, "c.log");

        System.out.println("logfileMap:" + logfileMap);

        Map<String, Integer> logfileInverseMap = getInverseMap(logfileMap);

        System.out.println("logfileInverseMap:" + logfileInverseMap);
    }

    @Test
    public void bimapTest() {
        BiMap<Integer, String> logfileMap = HashBiMap.create();
        logfileMap.put(1, "a.log");
        logfileMap.put(2, "b.log");
        logfileMap.put(3, "c.log");
        System.out.println("logfileMap:" + logfileMap);
        BiMap<String, Integer> filelogMap = logfileMap.inverse();
        System.out.println("filelogMap:" + filelogMap);
    }

    @Test
    public void bimapTest2() {
        BiMap<Integer, String> logfileMap = HashBiMap.create();
        logfileMap.put(1, "a.log");
        logfileMap.put(2, "b.log");
        logfileMap.put(3, "c.log");
        logfileMap.put(4, "d.log");
        logfileMap.put(5, "d.log");
    }

    @Test
    public void bimapTestForcePut() {
        BiMap<Integer, String> logfileMap = HashBiMap.create();
        logfileMap.put(1, "a.log");
        logfileMap.put(2, "b.log");
        logfileMap.put(3, "c.log");

        logfileMap.put(4, "d.log");
        logfileMap.forcePut(5, "d.log");
        System.out.println("logfileMap:" + logfileMap);
    }

    @Test
    public void bimapTestInverse() {
        BiMap<Integer, String> logfileMap = HashBiMap.create();
        logfileMap.put(1, "a.log");
        logfileMap.put(2, "b.log");
        logfileMap.put(3, "c.log");
        System.out.println("logfileMap:" + logfileMap);
        BiMap<String, Integer> filelogMap = logfileMap.inverse();
        System.out.println("filelogMap:" + filelogMap);

        logfileMap.put(4, "d.log");

        System.out.println("logfileMap:" + logfileMap);
        System.out.println("filelogMap:" + filelogMap);
    }

}
