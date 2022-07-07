package top.lijingyuan.guava.learning.eventbus.monitor;

/**
 * TargetMonitor
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public interface TargetMonitor {

    void startMonitor() throws Exception;

    void stopMonitor() throws Exception;

}
