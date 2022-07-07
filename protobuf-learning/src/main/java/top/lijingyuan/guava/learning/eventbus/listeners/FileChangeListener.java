package top.lijingyuan.guava.learning.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.guava.learning.eventbus.event.FileChangerEvent;

/**
 * FileChangeListener
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
@Slf4j
public class FileChangeListener {

    @Subscribe
    public void onChange(final FileChangerEvent event) {
        log.info("{}-{}", event.getPath(), event.getKind());
    }

}
