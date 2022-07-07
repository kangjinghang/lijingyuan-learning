package top.lijingyuan.guava.learning.eventbus.event;

import lombok.Data;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * FileChangerEvent
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-25
 * @since 1.0.0
 */
@Data
public class FileChangerEvent {

    private final Path path;

    private final WatchEvent.Kind<?> kind;

    public FileChangerEvent(Path path, WatchEvent.Kind<?> kind) {
        this.path = path;
        this.kind = kind;
    }
}
