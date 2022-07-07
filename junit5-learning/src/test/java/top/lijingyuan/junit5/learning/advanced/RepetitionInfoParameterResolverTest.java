package top.lijingyuan.junit5.learning.advanced;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.RepeatedTest.LONG_DISPLAY_NAME;

/**
 * TestInfoParameterResolverTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
@DisplayName("RepetitionInfoParameterResolverTest")
class RepetitionInfoParameterResolverTest {

    @DisplayName("prefix---")
    @RepeatedTest(value = 3, name = LONG_DISPLAY_NAME)
    void repeatTest(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        System.out.println(repetitionInfo.getCurrentRepetition() + "--" + repetitionInfo.getTotalRepetitions());
        System.out.println(testInfo.getDisplayName());
        System.out.println("--------------");
    }

}
