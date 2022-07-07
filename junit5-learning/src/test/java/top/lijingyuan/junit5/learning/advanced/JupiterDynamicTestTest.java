package top.lijingyuan.junit5.learning.advanced;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * JupiterDynamicTestTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
@DisplayName("Example for Dynamic Test")
class JupiterDynamicTestTest {

    @TestFactory
    Iterator<DynamicTest> dynamicTestsFromIterator() {
        return Arrays.asList(
                dynamicTest("1 dynamic test", () -> assertTrue(true)),
                dynamicTest("2 dynamic test", () -> assertEquals(10, 2 * 5))
        ).iterator();
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStream() {
        return Stream.of(
                dynamicTest("1 dynamic test", () -> assertTrue(true)),
                dynamicTest("2 dynamic test", () -> assertEquals(10, 2 * 5))
        );
    }

    @TestFactory
    Stream<DynamicTest> theUserTitleShouldBe3Star() {
        final List<String> list = this.queryFromDb();
        return Stream.of("JAVA", "SCALA", "GROOVY").map(e -> dynamicTest(String.format("[%s] in db", e), () -> {
            assertTrue(list.contains(e));
        }));
    }

    private List<String> queryFromDb() {
        return Arrays.asList("JAVA", "SCALA", "GROOVY", "CLOJURE", "JSHEEL");
    }

    @TestFactory
    DynamicNode dynamicTestsWithContainer() {
        return dynamicContainer("topic dynamic container",
                Stream.of(
                        dynamicTest("first level dynamic test", () -> assertTrue(true)),
                        dynamicContainer("second level dynamic container", Stream.of(
                                dynamicTest("second level <1> dynamic container", () -> assertTrue(true)),
                                dynamicTest("second level <2> dynamic container", () -> assertTrue(true))
                        ))
                ));
    }

    @TestFactory
    Stream<DynamicNode> loadDynamicTestFromFiles() throws IOException {
        return Files.walk(Paths.get("src/test/resources/testcase"), 1)
                .filter(path -> path.endsWith("txt"))
                .map(path ->
                        dynamicTest("load test file from class uri", path.toUri(),
                                () -> checkFileContent(path))
                );
    }

    private void checkFileContent(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        String line8 = lines.get(7);
        assertEquals("8", line8);
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("setUp@BeforeEach-->" + testInfo);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println("tearDown@AfterEach-->" + testInfo);
    }

}
