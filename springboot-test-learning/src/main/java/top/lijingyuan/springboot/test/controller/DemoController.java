package top.lijingyuan.springboot.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-06-24
 * @since 1.0.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

}
