package top.lijingyuan.scheduler.elastic.job;


import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * JobMain
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class JobMain {

    private static final int ZOOKEEPER_PORT = 2181;

    private static final String ZOOKEEPER_CONNECTION_STRING = "localhost:" + ZOOKEEPER_PORT;

    private static final String JOB_NAMESPACE = "elastic-job-example-java";

    public static void main(String[] args) {
        generateTestFiles();
        CoordinatorRegistryCenter registryCenter = setUpRegistryCenter();
        startJob(registryCenter);
    }

    private static void generateTestFiles() {
            for (int i = 0; i < 10; i++) {
            FileBackupJob.files.add(new FileCustom(String.valueOf(i + 10), "文件" + (i + 10), "text", "content" + (i + 10)));
            FileBackupJob.files.add(new FileCustom(String.valueOf(i + 20), "文件" + (i + 20), "image", "content" + (i + 20)));
            FileBackupJob.files.add(new FileCustom(String.valueOf(i + 30), "文件" + (i + 30), "radio", "content" + (i + 30)));
            FileBackupJob.files.add(new FileCustom(String.valueOf(i + 40), "文件" + (i + 40), "video", "content" + (i + 40)));
        }
    }

    private static void startJob(CoordinatorRegistryCenter registryCenter) {
        new ScheduleJobBootstrap(registryCenter, new FileBackupJob(), createJobConfiguration()).schedule();
    }

    private static JobConfiguration createJobConfiguration() {
        return JobConfiguration.newBuilder("files-job", 1)
                .cron("0/3 * * * * ?")
                // 记录日志策略
//                .jobErrorHandlerType("LOG")
                // 抛出异常策略
//                .jobErrorHandlerType("THROW")
                // 忽略异常策略
                .jobErrorHandlerType("IGNORE")
                .build();
    }

    private static CoordinatorRegistryCenter setUpRegistryCenter() {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING, JOB_NAMESPACE);
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(100);
        ZookeeperRegistryCenter registryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        registryCenter.init();
        return registryCenter;
    }


}
