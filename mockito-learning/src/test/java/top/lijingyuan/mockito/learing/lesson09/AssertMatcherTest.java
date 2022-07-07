package top.lijingyuan.mockito.learing.lesson09;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.either;
import static org.junit.Assert.assertThat;
import static top.lijingyuan.mockito.learing.lesson09.CompareNumber.gt;
import static top.lijingyuan.mockito.learing.lesson09.CompareNumber.lt;

/**
 * AssertMatcherTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-27
 * @since 1.0.0
 */
public class AssertMatcherTest {

    @Test
    public void test() {
        int i = 10;
        assertThat(i, equalTo(10));

        assertThat(i, not(equalTo(20)));

        assertThat(i, is(10));

        assertThat(i, is(not(20)));
    }

    @Test
    public void test2() {
        double price = 23.45;

        assertThat(price, either(equalTo(23.45)).or(equalTo(23.54)));

        assertThat(price, both(equalTo(23.45)).and(not(equalTo(23.54))));

        assertThat(price, anyOf(is(23.45), is(43.43), is(54.34), not(45.67)));

        assertThat(price, allOf(is(23.45), not(equalTo(23.54)), not(equalTo(45.54))));
    }

    @Test
    public void test3() {
        double price = 23.65;
        assertThat("the double value assertion failed", price, either(equalTo(23.45)).or(equalTo(23.54)));
    }

    @Test
    public void test4() {
        assertThat(10, gt(5));
        assertThat(10, lt(212));

        assertThat(10, both(gt(5)).and(lt(12)));
    }

}
