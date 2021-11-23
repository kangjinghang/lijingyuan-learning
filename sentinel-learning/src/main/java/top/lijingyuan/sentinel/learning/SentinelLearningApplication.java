package top.lijingyuan.sentinel.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * QuickStartApplication
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-11-22
 * @since 1.0.0
 */
@SpringBootApplication
@EnableAsync
public class SentinelLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelLearningApplication.class, args);
    }

}
