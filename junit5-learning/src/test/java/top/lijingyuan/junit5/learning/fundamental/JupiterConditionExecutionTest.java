package top.lijingyuan.junit5.learning.fundamental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

/**
 * JupiterConditionExecutionTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
@DisplayName("< Example of Jupiter build-in Condition Execution >")
class JupiterConditionExecutionTest {

    @Test
    @DisabledOnJre(value = JRE.JAVA_14, disabledReason = "disabled due to JDK 14.")
    void disabledWhenJDK14() {
        System.out.println(System.getProperty("java.version"));
    }

    @DisplayName("only execution on jdk [8-14]")
    @Test
    @EnabledForJreRange(max = JRE.JAVA_14)
    void enabledWhenJDKMatchRange() {

    }

    @DisplayName("not execution on jdk [10-14]")
    @Test
    @EnabledForJreRange(min = JRE.JAVA_10, max = JRE.JAVA_14)
    void disabledWhenJDKMatchRange() {

    }

    @Test
    @EnabledOnOs(OS.MAC)
    void enabledOnMac() {

    }

    @Test
    @DisabledOnOs({OS.MAC, OS.AIX})
    void disabledOnMac() {

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "M2_HOME", matches = ".*")
    void enabledIfEnvironmentContainsMavenHome() {

    }

    @Test
    @EnabledIfSystemProperty(named = "env", matches = "dev")
    void enabledIfSystemProperty() {

    }

    @Test
    @EnabledIf("customCondition")
    void customEnabledIf() {

    }

    private boolean customCondition() {
        return Boolean.FALSE;
    }

    @BeforeEach
    void setUp() {
        System.getProperties().put("env", "sit");
    }

}
