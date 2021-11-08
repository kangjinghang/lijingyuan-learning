package top.lijingyuan.junit5.learning.advanced;

import org.junit.jupiter.api.*;

/**
 * TestReporterParameterResolverTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
class TestReporterParameterResolverTest {

    @BeforeEach
    void repeatTest(RepetitionInfo repetitionInfo, TestInfo testInfo, TestReporter reporter) {
        reporter.publishEntry("displayName", testInfo.getDisplayName());
        reporter.publishEntry("repetitions", String.valueOf(repetitionInfo.getTotalRepetitions()));
    }

    @DisplayName("Simple test for test reporter")
    @RepeatedTest(3)
    void simpleTest(TestReporter reporter) {
        reporter.publishEntry("from simple test");
    }

}
