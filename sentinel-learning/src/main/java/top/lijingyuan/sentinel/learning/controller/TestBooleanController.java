package top.lijingyuan.sentinel.learning.controller;

import com.alibaba.csp.sentinel.SphO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestBooleanController {

    @GetMapping("/boolean")
    public boolean hello() {

        // 1.进行限流控制
        if (SphO.entry("Sentinel_Boolean")) {//限流入口
            try {
                // 被保护的资源
                System.out.println("访问成功");
                return true;
            } finally {
                // SphO.entry(xxx)需要与 SphO.exit()方法成对出现，否则会导致调用链记录异常，抛出ErrorEntryFreeException异常
                SphO.exit(); // 限流出口，
            }
        } else {
            // 被限流或者降级的处理
            System.out.println("系统繁忙，请稍候");
            return false;
        }

    }
}