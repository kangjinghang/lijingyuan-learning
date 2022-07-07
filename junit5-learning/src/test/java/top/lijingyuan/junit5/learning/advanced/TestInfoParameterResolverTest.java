package top.lijingyuan.junit5.learning.advanced;

import org.junit.jupiter.api.*;

/**
 * TestInfoParameterResolverTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
@DisplayName("TestInfoParameterResolverTest")
class TestInfoParameterResolverTest {

    @DisplayName("display name of init@BeforeAll")
    @BeforeAll
    static void init(TestInfo testInfo) {
        System.out.println("==============");
        System.out.println("getDisplayName: " + testInfo.getDisplayName());
        System.out.println("getTestClass: " + testInfo.getTestClass());
        System.out.println("getTestMethod: " + testInfo.getTestMethod());
        System.out.println("getTags: " + testInfo.getTags());
        System.out.println("==============");
    }

    @DisplayName("display name of setup@BeforeEach")
    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("--------------");
        System.out.println("getDisplayName: " + testInfo.getDisplayName());
        System.out.println("getTestClass: " + testInfo.getTestClass());
        System.out.println("getTestMethod: " + testInfo.getTestMethod());
        System.out.println("getTags: " + testInfo.getTags());
        System.out.println("--------------");
    }

    @DisplayName("test display name")
    @Tag("only-test")
    @Test
    void simpleTest(TestInfo testInfo) {
        System.out.println("*************");
        System.out.println("getDisplayName: " + testInfo.getDisplayName());
        System.out.println("getTestClass: " + testInfo.getTestClass());
        System.out.println("getTestMethod: " + testInfo.getTestMethod());
        System.out.println("getTags: " + testInfo.getTags());
        System.out.println("*************");
    }

}
