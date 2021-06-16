package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.BoundType;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeMap;
import org.junit.Test;

import java.util.NavigableMap;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * RangeTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class RangeTest {

    /**
     * {x|a<=x<=b}
     */
    @Test
    public void testCreate() {
        Range<Integer> closedRange = Range.closed(0, 9);
        System.out.println(closedRange);
        assertThat(closedRange.contains(5), equalTo(true));
        assertThat(closedRange.lowerEndpoint(), equalTo(0));
        assertThat(closedRange.upperEndpoint(), equalTo(9));
    }

    /**
     * {x|a<x<b}
     */
    @Test
    public void testOpenCreate() {
        Range<Integer> openRange = Range.open(0, 9);
        System.out.println(openRange);
        assertThat(openRange.contains(5), equalTo(true));
        assertThat(openRange.lowerEndpoint(), equalTo(0));
        assertThat(openRange.upperEndpoint(), equalTo(9));
        assertThat(openRange.contains(0), equalTo(false));
        assertThat(openRange.contains(9), equalTo(false));
    }

    /**
     * {x|a<x<=b}
     */
    @Test
    public void testOpenClosedCreate() {
        Range<Integer> openClosedRange = Range.openClosed(0, 9);
        System.out.println(openClosedRange);
        assertThat(openClosedRange.contains(5), equalTo(true));
        assertThat(openClosedRange.lowerEndpoint(), equalTo(0));
        assertThat(openClosedRange.upperEndpoint(), equalTo(9));
        assertThat(openClosedRange.contains(0), equalTo(false));
        assertThat(openClosedRange.contains(9), equalTo(true));
    }

    /**
     * {x|a<=x<b}
     */
    @Test
    public void testClosedOpenCreate() {
        Range<Integer> openClosedRange = Range.closedOpen(0, 9);
        System.out.println(openClosedRange);
        assertThat(openClosedRange.contains(5), equalTo(true));
        assertThat(openClosedRange.lowerEndpoint(), equalTo(0));
        assertThat(openClosedRange.upperEndpoint(), equalTo(9));
        assertThat(openClosedRange.contains(0), equalTo(true));
        assertThat(openClosedRange.contains(9), equalTo(false));
    }

    /**
     * {x|x>a}
     */
    @Test
    public void testGreaterThanCreate() {
        Range<Integer> greaterThanRange = Range.greaterThan(10);
        assertThat(greaterThanRange.contains(10), equalTo(false));
        assertThat(greaterThanRange.contains(Integer.MAX_VALUE), equalTo(true));
    }

    @Test
    public void testMapRange() {
        TreeMap<String, Integer> treeMap = Maps.newTreeMap();
        treeMap.put("Scala", 1);
        treeMap.put("Guava", 2);
        treeMap.put("Java", 3);
        treeMap.put("Kafka", 4);
        System.out.println(treeMap);
        NavigableMap<String, Integer> subMap = Maps.subMap(treeMap, Range.closed("Guava", "Kafka"));
        System.out.println(subMap);
        subMap = Maps.subMap(treeMap, Range.open("Guava", "Kafka"));
        System.out.println(subMap);
        subMap = Maps.subMap(treeMap, Range.openClosed("Guava", "Kafka"));
        System.out.println(subMap);
    }

    @Test
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
    }

    @Test
    public void testRangeMap() {
        TreeRangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(0,10),"F");
        rangeMap.put(Range.closed(11,20),"E");
        rangeMap.put(Range.closed(21,30),"D");
        System.out.println(rangeMap);
        assertThat(rangeMap.get(12), equalTo("E"));
    }

}
