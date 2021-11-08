package top.lijingyuan.junit5.learning.fundamental;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CustomJupiterTaggingTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
@Env.All
class CustomJupiterTaggingTest {

    private static List<String> list;

    @BeforeAll
    static void init() {
        list = Arrays.asList("HELLO", "JAVA", "JUNIT", "JUPITER");
    }

    @Env.Dev
    @DisplayName("[the size should be four]")
    @Test
    void listSizeShouldEquals4() {
        int size = list.size();
        assertEquals(4, size);
    }

    @Env.Sit
    @DisplayName("[the list contains 'JAVA' element]")
    @Test
    void listContainsJavaElement() {
        String java = "JAVA";
        boolean existing = list.contains(java);
        assertTrue(existing);

        assertTrue(() -> list.contains(java));
    }

    @Env.Uat
    @DisplayName("[the immutable list only support read operation]")
    @Test
    void immutableListCouldNotUpdate() {
        int index = 0;
        String expected = "HELLO";

//        assertEquals(expected, firstElement);
        Executable executable = () -> {
            String firstElement = list.remove(index);
            assertEquals(expected, firstElement);
        };
        assertThrows(UnsupportedOperationException.class, executable);
    }

}
