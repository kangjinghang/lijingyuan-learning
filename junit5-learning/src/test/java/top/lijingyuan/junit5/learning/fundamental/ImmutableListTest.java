package top.lijingyuan.junit5.learning.fundamental;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ImmutableListTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("<The unit test for Java Immutable List Features>")
class ImmutableListTest {

    private final List<String> list = Arrays.asList("HELLO", "JAVA", "JUNIT", "JUPITER");

    private static String ENV = "N/A";

    @BeforeAll
    static void init() {
        ENV = System.getenv().getOrDefault("env", "N/A");
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("< setup " + testInfo.getTestMethod().get() + " >");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("< tearDown " + testInfo.getTestMethod().get() + " >");
    }

    @AfterAll
    static void destroy() {
        System.out.println("--------destroy--------");
    }

    @DisplayName("The basic usage of jupiter")
    @Nested
    class NestedBasic {

        @DisplayName("[the size should be four]")
        @Test
        void listSizeShouldEquals4() {
            int size = list.size();
            assertEquals(4, size);
        }

        @DisplayName("[the list contains 'JAVA' element]")
        @Test
        void listContainsJavaElement() {
            String java = "JAVA";
            boolean existing = list.contains(java);
            assertTrue(existing);

            assertTrue(() -> list.contains(java));
        }

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

    @DisplayName("repeated feature")
    @Nested
    class NestedRepeated {
        @RepeatedTest(3)
        void repeatTest() {
            assertTrue(() -> list.contains("JUNIT"));
        }

        @RepeatedTest(4)
        void repeatWithIndex(RepetitionInfo info) {
            String ele;
            switch (info.getCurrentRepetition()) {
                case 1:
                    ele = "HELLO";
                    break;
                case 2:
                    ele = "JAVA";
                    break;
                case 3:
                    ele = "JUNIT";
                    break;
                case 4:
                    ele = "JUPITER";
                    break;
                default:
                    ele = "N/A";
            }
            assertEquals(ele, list.get(info.getCurrentRepetition() - 1));
        }

        @DisplayName("Repeat Assert Immutable list element ==>")
        @RepeatedTest(value = 4, name = "{displayName} {currentRepetition}/{totalRepetitions}")
        void repeatWithDetailInformation(RepetitionInfo info) {
            String ele;
            switch (info.getCurrentRepetition()) {
                case 1:
                    ele = "HELLO";
                    break;
                case 2:
                    ele = "JAVA";
                    break;
                case 3:
                    ele = "JUNIT";
                    break;
                case 4:
                    ele = "JUPITER";
                    break;
                default:
                    ele = "N/A";
            }
            assertEquals(ele, list.get(info.getCurrentRepetition() - 1));
        }
    }

    @DisplayName("assert timeout")
    @Nested
    class NestedTimeout {

        @Order(1)
        @Timeout(value = 30)
        @DisplayName("Simple test for assertTimeout API of jupiter")
        @Test
        void testAssertions() {
            ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                blockingQueue.offer("TEST");
            }).start();
            assertTimeout(Duration.ofSeconds(2), () -> {
                assertEquals(blockingQueue.take(), "TEST");
            });
        }

        @Order(2)
        @DisplayName("Simple test for preemptively assertions API of jupiter")
        @Test
        void testPreemptivelyAssertions() {
            ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);
            assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                blockingQueue.take();
            });
        }
    }

    @DisplayName("assumptions")
    @Nested
    class NestedAssumption {

        @Test
        void onlyExecutionAtSitEnv() {
            Assumptions.assumingThat(() -> ENV.equals("sit"), () -> {
                // given
                // when
                // then
            });
            Assumptions.assumeTrue(ENV.equals("sit"));
            ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);
            assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                blockingQueue.take();
            });
        }

    }

    @Disabled("disabled due to one of assertions failure.")
    @DisplayName("[the immutable list only support read but update]")
    @Test
    void immutableListCanReadButUpdate() {
//        assertEquals("HELLO", list.remove(0));
//        assertEquals("HELLO", list.get(0));
//        assertEquals("JAVA", list.get(1));
        assertAll("assert read and update mixed operation", Stream.of(
                () -> assertEquals("HELLO", list.remove(0)),
                () -> assertEquals("HELLO", list.get(0)),
                () -> assertEquals("JAVA", list.remove(1))
        ));
    }

    @Order(3)
    @DisplayName("Long Display Name...")
    @RepeatedTest(value = 2, name = RepeatedTest.LONG_DISPLAY_NAME)
    void repeatLongDisplayName(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        assertEquals(String.format("Long Display Name... :: repetition %d of %d",
                repetitionInfo.getCurrentRepetition(), repetitionInfo.getTotalRepetitions()), testInfo.getDisplayName());
    }

}
