package top.lijingyuan.springboot.test.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import top.lijingyuan.springboot.test.service.impl.UserServiceImpl;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * UserServiceTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-06-25
 * @since 1.0.0
 */
@ExtendWith(SpringExtension.class)
@RestClientTest(value = UserServiceImpl.class)
//@RestClientTest(value = UserServiceImpl.class, includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
//})
// 下面一行是关键，如果 UserServiceImpl 中的 RestTemplate 不是注入进来，而是自己初始化的
//@AutoConfigureWebClient(registerRestTemplate = true)
class UserServiceTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private UserService client;

    @Test
    void testServiceCall() {
        this.server.expect(requestTo("http://localhost:8080/users"))
                .andRespond(withSuccess("<users></users>", MediaType.TEXT_PLAIN));

        String userServiceResponse = client.testUserService();

        Assertions.assertEquals(userServiceResponse, "<users></users>");
    }

}