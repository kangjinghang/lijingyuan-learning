package top.lijingyuan.sentinel.learning.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WhiteBlackController {

    // 定义限流资源和限流讲解回调函数
    @SentinelResource(value = "Sentinel_Rule", blockHandler = "exceptionHandler")
    @GetMapping("/origin")
    public String hello() {
        return "Hello Sentinel!";
    }

    // blockHandler函数，原方法调用被限流/降级/系统保护的时候调用
    public String exceptionHandler(BlockException ex) {
        ex.printStackTrace();
        return "系统繁忙，请稍候";
    }

    /**
     * 白名单设置
     **/
    @PostConstruct
    private static void initWhiteRules() {
        // 1.创建存放授权规则的集合
        List<AuthorityRule> rules = new ArrayList<AuthorityRule>();

        // 2.创建授权规则
        AuthorityRule rule = new AuthorityRule();
        // 定义资源
        rule.setResource("Sentinel_Rule");
        // 设置授权模式 RuleConstant.AUTHORITY_WHITE ：白名单
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        // 设置白名单
        rule.setLimitApp("192.168.0.100");
        // 将授权规则添加到集合中
        rules.add(rule);

        // 3.加载授权规则
        AuthorityRuleManager.loadRules(rules);
    }

    /**
     * 黑名单设置
     **/
    @PostConstruct
    private static void initBlackRules() {
        // 1.创建存放授权规则的集合
        List<AuthorityRule> rules = new ArrayList<AuthorityRule>();

        // 2.创建授权规则
        AuthorityRule rule = new AuthorityRule();
        // 定义资源
        rule.setResource("Sentinel_Rule");
        // 设置授权模式 RuleConstant.AUTHORITY_BLACK ：黑名单
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        // 设置黑名单
        rule.setLimitApp("127.0.0.1");
        // 将授权规则添加到集合中
        rules.add(rule);

        // 3.加载授权规则
        AuthorityRuleManager.loadRules(rules);
    }

}