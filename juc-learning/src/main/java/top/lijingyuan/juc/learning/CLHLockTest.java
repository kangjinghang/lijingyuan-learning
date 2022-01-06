package top.lijingyuan.juc.learning;

public class CLHLockTest {
    private static int cnt = 0;

    public static void main(String[] args) throws Exception {
        final CLHLock lock = new CLHLock();

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                lock.lock();

                cnt++;

                lock.unLock();
            }).start();
        }
        // 让main线程休眠10秒，确保其他线程全部执行完
        Thread.sleep(10000);
        System.out.println();
        System.out.println("cnt----------->>>" + cnt);

    }
}