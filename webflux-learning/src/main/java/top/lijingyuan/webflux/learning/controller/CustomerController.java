package top.lijingyuan.webflux.learning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import top.lijingyuan.webflux.learning.dto.Customer;
import top.lijingyuan.webflux.learning.service.CustomerService;

import javax.annotation.Resource;
import java.util.List;

/**
 * CustomerController
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-30
 * @since 1.0.0
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Resource
    private CustomerService service;

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return service.loadAllCustomers();
    }

    //    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @GetMapping(value = "/stream")
    public Flux<Customer> getAllCustomersStream() {
        return service.loadAllCustomersStream();
    }

}
