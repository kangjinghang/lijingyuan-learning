package top.lijingyuan.sentinel.learning.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TestController
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-22
 * @since 1.0.0
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        // 1.进行限流控制
        try (Entry entry = SphU.entry("Hello")) { // 限流入口、
            try {
                TimeUnit.MICROSECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello Sentinel!"; // 被保护的资源
        } catch (BlockException e) {
            e.printStackTrace();
            return "系统繁忙，请稍候";// 被限流或者降级的处理
        }
    }

    @PostConstruct
    private void initFlowRules() {
        // 1.创建存放限流规则的集合
        List<FlowRule> rules = new ArrayList<>();
        // 2.创建限流规则
        FlowRule rule = new FlowRule();
        // 定义资源
        rule.setResource("Hello");
        // 定义限流规则类型,RuleConstant.FLOW_GRADE_QPS：OPS类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 定义OPS每秒最多只能通过的请求个数
        rule.setCount(2);
        // 将限流规则添加到集合中
        rules.add(rule);
        // 3.加载限流规则
        FlowRuleManager.loadRules(rules);
    }

}
