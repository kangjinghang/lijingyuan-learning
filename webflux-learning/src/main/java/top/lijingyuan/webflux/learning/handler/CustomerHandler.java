package top.lijingyuan.webflux.learning.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.lijingyuan.webflux.learning.dao.CustomerDao;
import top.lijingyuan.webflux.learning.dto.Customer;

import javax.annotation.Resource;

/**
 * CustomerHandler
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-30
 * @since 1.0.0
 */
@Service
public class CustomerHandler {

    @Resource
    private CustomerDao dao;

    public Mono<ServerResponse> loadCustomers(ServerRequest request) {
        final Flux<Customer> customersList = dao.getCustomersList();
        return ServerResponse.ok().body(customersList, Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest request) {
        final int id = Integer.parseInt(request.pathVariable("input"));
        Mono<Customer> customer = dao.getCustomersList().filter(c -> c.getId() == id).next();
        return ServerResponse.ok().body(customer, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        final Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        final Mono<String> response = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
        return ServerResponse.ok().body(response, String.class);
    }


}
