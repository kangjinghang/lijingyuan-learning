package top.lijingyuan.sentinel.learning.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SysController {

    // 定义限流资源和限流讲解回调函数
    @SentinelResource(entryType = EntryType.IN)
    @GetMapping("/sys")
    public String hello() {

        return "Hello Sentinel!";
    }

    /**
     * 定义系统自适应规则
     */
    @PostConstruct
    private void initSystemRule() {
        // 1.创建系统自适应规则的集合
        List<SystemRule> rules = new ArrayList<>();
        // 2.创建系统自适应规则
        SystemRule rule = new SystemRule();
        // 设置根据入口QPS规则
        rule.setQps(2);
        // 将系统自适应规则添加到集合中
        rules.add(rule);

        // 3.加载系统自适应规则
        SystemRuleManager.loadRules(rules);
    }

}