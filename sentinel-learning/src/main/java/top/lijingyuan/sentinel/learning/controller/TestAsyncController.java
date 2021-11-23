package top.lijingyuan.sentinel.learning.controller;

import com.alibaba.csp.sentinel.AsyncEntry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lijingyuan.sentinel.learning.service.AsyncService;

@RestController
public class TestAsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async")
    public void hello() {

        //1.进行限流控制
        AsyncEntry asyncEntry = null;
        try {
            asyncEntry = SphU.asyncEntry("Sentinel_Async"); // 限流入口
            asyncService.hello(); // 异步方法调用
        } catch (BlockException e) { // 被限流或者降级的处理
            System.out.println("系统繁忙，请稍候");
        } finally {
            if (asyncEntry != null) {
                asyncEntry.exit(); // 限流出口，
            }
        }
    }

}