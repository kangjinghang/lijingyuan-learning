package top.lijingyuan.guava.learning;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableMap.of;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * JoinerTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class JoinerTest {

    private final List<String> stringList = Arrays.asList("Google",
            "Guava", "Java", "Scala", "Kafka");

    private final List<String> stringListWithNullValue = Arrays.asList("Google",
            "Guava", "Java", "Scala", null);

    @Test
    public void test() {
        System.out.println("hello junit");
    }

    @Test
    public void testJoinOnJoin() {
        String result = Joiner.on("#").join(stringList);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }

    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue() {
        String result = Joiner.on("#").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }

    @Test
    public void testJoinOnJoinWithNullValueButSkip() {
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoinOnJoinWithNullValueButUseDefaultValue() {
        String result = Joiner.on("#").useForNull("Default").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Default"));
    }

    @Test
    public void testJoinOnAppendToStringBuilder() {
        final StringBuilder builder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on("#").useForNull("Default").appendTo(builder, stringListWithNullValue);
        assertThat(resultBuilder, sameInstance(builder));
        assertThat(resultBuilder.toString(), equalTo("Google#Guava#Java#Scala#Default"));
    }

    final String targetFileName = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/test/resources/guava-joiner.txt";

    @Test
    public void testJoinOnAppendToWriter() throws IOException {
        try (FileWriter fileWriter = new FileWriter(targetFileName)) {
            Joiner.on("#").useForNull("Default").appendTo(fileWriter, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        }
    }

    @Test
    public void testJoiningByStream() {
        String result = stringListWithNullValue.stream().filter(item -> item != null && !item.isEmpty()).collect(Collectors.joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoiningByStreamWithDefaultValue() {
        String result = stringListWithNullValue.stream()
                .map(this::defaultValue)
                .collect(Collectors.joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala#Default"));
    }

    private String defaultValue(String item) {
        return item == null || item.isEmpty() ? "Default" : item;
    }

    private final Map<String, String> stringMap = of("Hello", "Guava", "Java", "Scala");

    @Test
    public void testJoinOnWithMap() {
        assertThat(Joiner.on("#").withKeyValueSeparator("=").join(stringMap), equalTo("Hello=Guava#Java=Scala"));
    }

    final String targetMapFileName = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/test/resources/guava-joiner-map.txt";

    @Test
    public void testJoinOnWithMapToAppendable() throws IOException {
        try (FileWriter fileWriter = new FileWriter(targetMapFileName)) {
            Joiner.on("#").withKeyValueSeparator("=").appendTo(fileWriter, stringMap);
            assertThat(Files.isFile().test(new File(targetMapFileName)), equalTo(true));
        }
    }

}
