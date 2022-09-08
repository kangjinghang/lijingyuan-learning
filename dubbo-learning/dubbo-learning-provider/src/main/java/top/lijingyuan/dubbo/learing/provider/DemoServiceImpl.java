package top.lijingyuan.dubbo.learing.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import top.lijingyuan.dubbo.learning.DemoService;

/**
 * DemoServiceImpl
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-08-03
 * @since 1.0.0
 */
@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name;
    }

}