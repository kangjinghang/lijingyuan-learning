package top.lijingyuan.webflux.learning.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.lijingyuan.webflux.learning.dao.CustomerDao;
import top.lijingyuan.webflux.learning.dto.Customer;

import javax.annotation.Resource;

/**
 * CustomerStreamHandler
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-30
 * @since 1.0.0
 */
@Service
public class CustomerStreamHandler {

    @Resource
    private CustomerDao dao;

    public Mono<ServerResponse> loadCustomers(ServerRequest request) {
        final Flux<Customer> customersList = dao.getCustomersStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersList, Customer.class);
    }

}
