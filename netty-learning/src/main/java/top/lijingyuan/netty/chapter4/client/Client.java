package top.lijingyuan.netty.chapter4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import top.lijingyuan.netty.chapter4.client.codec.OrderFrameDecoder;
import top.lijingyuan.netty.chapter4.client.codec.OrderFrameEncoder;
import top.lijingyuan.netty.chapter4.client.codec.OrderProtocolDecoder;
import top.lijingyuan.netty.chapter4.client.codec.OrderProtocolEncoder;
import top.lijingyuan.netty.chapter4.client.handler.ClientIdleCheckHandler;
import top.lijingyuan.netty.chapter4.client.handler.KeepAliveHandler;
import top.lijingyuan.netty.chapter4.common.RequestMessage;
import top.lijingyuan.netty.chapter4.common.order.OrderOperation;
import top.lijingyuan.netty.chapter4.util.IdUtil;

import java.util.concurrent.ExecutionException;


/**
 * Client
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        KeepAliveHandler keepAliveHandler = new KeepAliveHandler();

        bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new ClientIdleCheckHandler());

                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast(new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolDecoder());
                        pipeline.addLast(new OrderProtocolEncoder());

                        // 需要编码，所以放在后面
                        pipeline.addLast(keepAliveHandler);
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.sync();

//        for (int i = 0; i < 10000; i++) {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), new OrderOperation(1001, "tudou"));
        channelFuture.channel().writeAndFlush(requestMessage);
//        }

        channelFuture.channel().closeFuture().get();
    }

}
