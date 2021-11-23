package top.lijingyuan.sentinel.learning.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DegradeController {

    // 定义限流资源和限流讲解回调函数
    @SentinelResource(value = "/Sentinel_Rule", fallback = "exceptionHandler")
    @GetMapping("degrade")
    public String hello() {

        return "Hello Sentinel!";
    }

    // 降级方法
    public String exceptionHandler() {
        return "系统繁忙，请稍候";
    }

    @PostConstruct
    private void initDegradeRule() {
        // 1.创建存放熔断降级规则的集合
        List<DegradeRule> rules = new ArrayList<>();

        // 2.创建熔断降级规则
        DegradeRule rule = new DegradeRule();
        // 定义资源
        rule.setResource("Sentinel_Rule");
        // 阈值
        rule.setCount(0.01);
        // 定义规则类型,RuleConstant.DEGRADE_GRADE_RT：熔断降级(秒级 RT)类型
        /*
         * 当资源的平均响应时间超过阈值（DegradeRule中的count，以ms为单位）之后，资源进入准降级状态。
         * 接下来如果持续进入 5 个请求，它们的 RT 都持续超过这个阈值，
         * 那么在接下的时间窗口（DegradeRule 中的 timeWindow，以 s 为单位）之内，
         * 将会抛出 DegradeException。
         */
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 降级的时间，单位为 s
        rule.setTimeWindow(10);
        // 将熔断降级规则添加到集合中
        rules.add(rule);

        // 3.加载熔断降级规则
        DegradeRuleManager.loadRules(rules);
    }

}