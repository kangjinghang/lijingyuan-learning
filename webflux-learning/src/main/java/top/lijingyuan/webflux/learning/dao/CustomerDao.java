package top.lijingyuan.webflux.learning.dao;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import top.lijingyuan.webflux.learning.dto.Customer;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CustomerDao
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-30
 * @since 1.0.0
 */
@Repository
public class CustomerDao {

    private static void sleepExecution(int i) {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers() {
        return IntStream.range(1, 2)
                .peek(CustomerDao::sleepExecution)
                .peek(i -> System.out.println("processing count:" + i))
                .mapToObj(i -> new Customer(i, "customer" + i))
                .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomersStream() {
        return Flux.range(1, 1)
                .delayElements(Duration.ofMillis(1000))
                .doOnNext(i -> System.out.println("processing count in stream flow:" + i))
                .map(i -> new Customer(i, "customer" + i));
    }

    public Flux<Customer> getCustomersList() {
        return Flux.range(1, 50)
                .doOnNext(i -> System.out.println("processing count in stream flow:" + i))
                .map(i -> new Customer(i, "customer" + i));
    }

}
