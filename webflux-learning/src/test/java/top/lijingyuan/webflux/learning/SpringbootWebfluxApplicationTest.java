package top.lijingyuan.webflux.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * SpringbootWebfluxApplicationTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-31
 * @since 1.0.0
 */
//@WebFluxTest(CustomerController.class)
//@ContextConfiguration(classes = {SpringbootWebfluxApplication.class})
//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringbootWebfluxApplication.class)
@AutoConfigureWebTestClient
class SpringbootWebfluxApplicationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void run() {

    }

}