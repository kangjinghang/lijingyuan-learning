package top.lijingyuan.junit5.learning.fundamental;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JupiterTaggingTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
@Tag("dev")
@Tag("sit")
@Tag("uat")
class JupiterTaggingTest {

    private static List<String> list;

    @BeforeAll
    static void init() {
        list = Arrays.asList("HELLO", "JAVA", "JUNIT", "JUPITER");
    }

    @Tag("dev")
    @DisplayName("[the size should be four]")
    @Test
    void listSizeShouldEquals4() {
        int size = list.size();
        assertEquals(4, size);
    }

    @Tags(
            @Tag("sit")
    )
    @DisplayName("[the list contains 'JAVA' element]")
    @Test
    void listContainsJavaElement() {
        String java = "JAVA";
        boolean existing = list.contains(java);
        assertTrue(existing);

        assertTrue(() -> list.contains(java));
    }

    @Tag("uat")
    @Tag("prod")
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
