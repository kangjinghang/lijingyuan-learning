package top.lijingyuan.webflux.learning.rouer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.lijingyuan.webflux.learning.handler.CustomerHandler;
import top.lijingyuan.webflux.learning.handler.CustomerStreamHandler;

import javax.annotation.Resource;

/**
 * RouterConfig
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-30
 * @since 1.0.0
 */
@Configuration
public class RouterConfig {

    @Resource
    private CustomerHandler customerHandler;

    @Resource
    private CustomerStreamHandler customerStreamHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/customers", customerHandler::loadCustomers)
                .GET("/router/customers/stream", customerStreamHandler::loadCustomers)
                .GET("/router/customers/{input}", customerHandler::findCustomer)
                .POST("/router/customers/save", customerHandler::saveCustomer)
                .build();
    }

}
