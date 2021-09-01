package top.lijingyuan.netty.chapter1.echo;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Echoes back any received data from a client.
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-09-01
 * @since 1.0.0
 */
public class EchoServer {


    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

    }

}
