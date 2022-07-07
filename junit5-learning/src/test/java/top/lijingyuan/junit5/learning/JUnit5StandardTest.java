package top.lijingyuan.junit5.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * JUnit5StandardTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-07
 * @since 1.0.0
 */
class JUnit5StandardTest {

    @Test
    void listShouldContainsElement() {
        final List<String> list = Arrays.asList("JAVA", "JUNIT5", "JUPITER");
        final String element = "JAVA";
        boolean existing = list.contains(element);
        Assertions.assertTrue(existing);
    }

}
