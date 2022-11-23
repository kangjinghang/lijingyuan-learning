package top.lijingyuan.webflux.learning.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import top.lijingyuan.webflux.learning.dao.CustomerDao;
import top.lijingyuan.webflux.learning.dto.Customer;

import javax.annotation.Resource;
import java.util.List;

/**
 * CustomerService
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-30
 * @since 1.0.0
 */
@Service
public class CustomerService {

    @Resource
    private CustomerDao dao;

    public List<Customer> loadAllCustomers() {
        final long start = System.currentTimeMillis();
        final List<Customer> customers = dao.getCustomers();
        final long end = System.currentTimeMillis();
        System.out.println("Total execution time:" + (end - start));
        return customers;
    }

    public Flux<Customer> loadAllCustomersStream() {
        final long start = System.currentTimeMillis();
        final Flux<Customer> customers = dao.getCustomersStream();
        final long end = System.currentTimeMillis();
        System.out.println("Total execution time:" + (end - start));
        return customers;
    }


}
