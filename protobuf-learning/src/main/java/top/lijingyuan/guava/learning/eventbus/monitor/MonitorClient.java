package top.lijingyuan.guava.learning.eventbus.monitor;

import com.google.common.eventbus.EventBus;
import top.lijingyuan.guava.learning.eventbus.listeners.FileChangeListener;

/**
 * MonitorClient
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class MonitorClient {

    public static void main(String[] args) throws Exception {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());
        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus, "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/main/resources");
        monitor.startMonitor();
    }

}
