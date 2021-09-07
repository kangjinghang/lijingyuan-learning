package top.lijingyuan.netty.chapter4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import top.lijingyuan.netty.chapter4.client.codec.*;
import top.lijingyuan.netty.chapter4.common.order.OrderOperation;

import java.util.concurrent.ExecutionException;


/**
 * ClientV1
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class ClientV1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast(new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolDecoder());
                        pipeline.addLast(new OrderProtocolEncoder());
                        pipeline.addLast(new OperationToMessageRequestEncoder());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.sync();

        OrderOperation orderOperation =  new OrderOperation(1001, "tudou");
        channelFuture.channel().writeAndFlush(orderOperation);

        channelFuture.channel().closeFuture().get();
    }

}
