package top.lijingyuan.hadoop.learning.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

/**
 * NNServer 实现通信接口
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-11-28
 * @since 1.0.0
 */
public class NNServer implements RPCProtocol {


    public static void main(String[] args) throws IOException {
        // 启动服务
        RPC.Server server = new RPC.Builder(new Configuration())
                .setBindAddress("localhost")
                .setPort(8888)
                .setProtocol(RPCProtocol.class)
                .setInstance(new NNServer())
                .build();

        System.out.println("服务器器开始工作");
        server.start();
    }

    @Override
    public void mkdirs(String path) {
        System.out.println("服务器接收到了客户端请求" + path);
    }
}
