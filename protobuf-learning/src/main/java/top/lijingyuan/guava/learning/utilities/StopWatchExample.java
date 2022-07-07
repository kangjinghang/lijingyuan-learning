package top.lijingyuan.guava.learning.utilities;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * ElapsedExample
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-23
 * @since 1.0.0
 */
public class StopWatchExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopWatchExample.class);

    public static void main(String[] args) throws InterruptedException {
        process("4567722");
    }

    private static void process(String orderNo) throws InterruptedException {
        LOGGER.info("start process the order [{}]", orderNo);
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(1);
        LOGGER.info("The orderNo [{}] process successful and elapsed [{}]", orderNo, stopwatch.stop());
    }

}
