package top.lijingyuan.dubbo.learning;

import java.util.concurrent.CompletableFuture;

/**
 * DemoService
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-08-03
 * @since 1.0.0
 */
public interface DemoService {

    String sayHello(String name);

    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }

}
