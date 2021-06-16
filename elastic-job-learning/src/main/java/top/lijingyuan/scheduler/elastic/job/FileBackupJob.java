package top.lijingyuan.scheduler.elastic.job;


import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * FileBackupJob
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class FileBackupJob implements SimpleJob {

    private final int FETCH_SIZE = 1;

    public static List<FileCustom> files = new ArrayList<>();

    @Override
    public void execute(ShardingContext shardingContext) {
        int sharingItem = shardingContext.getShardingItem();
        System.out.println(String.format("作业分片：%d", sharingItem));
        List<FileCustom> fileCustoms = fetchUnBackupFiles(FETCH_SIZE);
        backupFiles(fileCustoms);
    }

    private void backupFiles(List<FileCustom> files) {
        for (FileCustom fileCustom : files) {
            fileCustom.setBackedUp(true);
            System.out.println(String.format("已备份文件：%s，文件类型：%s", fileCustom.getName(), fileCustom.getType()));
        }
    }

    private List<FileCustom> fetchUnBackupFiles(int count) {
        List<FileCustom> fetchList = new ArrayList<>();
        int num = 0;
        for (FileCustom fileCustom : files) {
            if (num >= count) {
                break;
            }
            if (!fileCustom.isBackedUp()) {
                fetchList.add(fileCustom);
                num++;
            }
        }
        System.out.printf("time：%s，获取文件：%d%n", LocalDateTime.now(), count);
        return fetchList;
    }
}
