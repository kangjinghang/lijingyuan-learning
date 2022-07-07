package top.lijingyuan.springboot.test.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.lijingyuan.springboot.test.service.UserService;

/**
 * UserServiceImpl
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-06-25
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

//    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String testUserService() {
        final String uri = "http://localhost:8080/users";

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Header", "value");
//        headers.set("Other-Header", "othervalue");

//        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.getForObject(uri, String.class);
    }
}
