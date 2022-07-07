package top.lijingyuan.guava.learning.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ListenableFutureExample
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-26
 * @since 1.0.0
 */
public class ListenableFutureExample {

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(2);
//        Future<Integer> future = service.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 10;
//        });
//        System.out.println(future.get());
        ListenableFuture<Integer> future = MoreExecutors.listeningDecorator(service).submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        future.addListener(() -> System.out.println("I am finished"), service);
        Futures.addCallback(future, new MyFutureCallBack(), service);

        System.out.println("hello");

        // java8
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        }).whenComplete((v, t) -> System.out.println("I am finished and the result is " + v));
        System.out.println("world");

    }

    /**
     * 有结果，用callBack
     */
    static class MyFutureCallBack implements FutureCallback<Integer> {

        @Override
        public void onSuccess(@Nullable Integer result) {
            System.out.println("I am finished and result is " + result);
        }

        @Override
        public void onFailure(Throwable t) {
            t.printStackTrace();
        }
    }

}
