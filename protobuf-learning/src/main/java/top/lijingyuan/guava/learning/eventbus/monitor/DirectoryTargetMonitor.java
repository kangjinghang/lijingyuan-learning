package top.lijingyuan.guava.learning.eventbus.monitor;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.guava.learning.eventbus.event.FileChangerEvent;

import java.nio.file.*;

/**
 * DirectoryTargetMonitor
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class DirectoryTargetMonitor implements TargetMonitor {

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath);
    }

    @Override
    public void startMonitor() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE);
        log.info("The directory [{}] is monitoring", path.toAbsolutePath());
        this.start = true;
        while (start) {
            WatchKey watchKey = null;
            try {
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(e -> {
                    WatchEvent.Kind<?> kind = e.kind();
                    Path p = (Path) e.context();
                    Path realPath = path.resolve(p);
                    eventBus.post(new FileChangerEvent(realPath, kind));
                });
            } catch (InterruptedException e) {
                this.start = false;
            } finally {
                if (watchKey != null) {
                    watchKey.reset();
                }
            }
        }
    }

    @Override
    public void stopMonitor() throws Exception {
        log.info("The directory [{}] will be stop...", path.toAbsolutePath());
        Thread.currentThread().interrupt();
//        this.start = false;
        this.watchService.close();
        log.info("The directory [{}] stopped", path.toAbsolutePath());
    }
}
