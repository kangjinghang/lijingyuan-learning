package top.lijingyuan.junit5.learning.advanced;

import org.junit.jupiter.api.DisplayName;

/**
 * RepeatedTestTemplateTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
@DisplayName("The custom repeat test template examples")
public class RepeatedTestTemplateTest {

    @Repeat(value = 3)
    void firstTest(RepeatInfo repeatInfo) {
        System.out.println("firstTest: " + repeatInfo);
    }

    @Repeat(value = 3)
    void secondTest() {
        System.out.println("secondTest");
    }

}
