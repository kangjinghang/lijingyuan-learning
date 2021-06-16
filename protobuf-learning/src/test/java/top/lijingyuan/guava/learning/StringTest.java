package top.lijingyuan.guava.learning;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * StringTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class StringTest {

    @Test
    public void testStringMethods() {
        assertThat(Strings.emptyToNull(""), nullValue());
        assertThat(Strings.nullToEmpty(null), equalTo(""));
        assertThat(Strings.nullToEmpty("hello"), equalTo("hello"));
        assertThat(Strings.commonPrefix("Hello", "Hit"), equalTo("H"));
        assertThat(Strings.commonPrefix("Hello", "Exit"), equalTo(""));
        assertThat(Strings.commonSuffix("Hello", "Echo"), equalTo("o"));
        assertThat(Strings.repeat("Hello", 3), equalTo("HelloHelloHello"));
        assertThat(Strings.isNullOrEmpty(null), equalTo(true));
        assertThat(Strings.isNullOrEmpty(""), equalTo(true));
        assertThat(Strings.padStart("Alex", 3, 'H'), equalTo("Alex"));
        assertThat(Strings.padStart("Alex", 5, 'H'), equalTo("HAlex"));
        assertThat(Strings.padEnd("Alex", 5, 'H'), equalTo("AlexH"));
    }

    @Test
    public void testCharMatcher() {
        assertThat(CharMatcher.javaDigit().matches('5'), equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'), equalTo(false));
        assertThat(CharMatcher.is('A').countIn("Alex sharing the Google Guava to us"), equalTo(1));
        assertThat(CharMatcher.breakingWhitespace().collapseFrom("   Hello Guava    ", '*'), equalTo("*Hello*Guava*"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello 1234 world"), equalTo("helloworld"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello 1234 world"), equalTo(" 1234 "));
    }

}
