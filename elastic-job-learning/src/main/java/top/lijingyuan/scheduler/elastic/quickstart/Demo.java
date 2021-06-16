package top.lijingyuan.scheduler.elastic.quickstart;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Demo
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class Demo {

    public static void main(String[] args) throws SchedulerException {
//        testTimer();
//        testSchedulerService();
        testQuartz();
    }

    public static void testQuartz() throws SchedulerException {
        // 创建一个scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // 创建JobDetail
        JobBuilder jobBuilder = JobBuilder.newJob(MyJob.class);
        jobBuilder.withIdentity("jobName", "jobGroupName");
        JobDetail jobDetail = jobBuilder.build();

        // 创建触发的CronTrigger，支持按日历调整
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerName", "triggerGroupName")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();

        // 创建触发的SimpleTrigger，简单的间隔调度
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerName", "triggerGroupName")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    public static void testSchedulerService() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleAtFixedRate(() -> System.out.println("time: " + LocalDateTime.now() + ",to do..."), 1, 2, TimeUnit.SECONDS);
    }

    public static void testTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("time: " + LocalDateTime.now() + ",to do...");
            }
        }, 1000, 2000);
    }

}
