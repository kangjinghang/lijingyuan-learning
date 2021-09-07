package top.lijingyuan.netty.chapter4.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import top.lijingyuan.netty.chapter4.server.codec.OrderFrameDecoder;
import top.lijingyuan.netty.chapter4.server.codec.OrderFrameEncoder;
import top.lijingyuan.netty.chapter4.server.codec.OrderProtocolDecoder;
import top.lijingyuan.netty.chapter4.server.codec.OrderProtocolEncoder;
import top.lijingyuan.netty.chapter4.server.handler.MetricHandler;
import top.lijingyuan.netty.chapter4.server.handler.OrderServerProcessHandler;

import java.util.concurrent.ExecutionException;

/**
 * Server
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class Server {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        MetricHandler metricHandler = new MetricHandler();
        UnorderedThreadPoolEventExecutor businessExecutor = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business"));

        serverBootstrap.channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
//                        pipeline.addLast(new LoggingHandler(LogLevel.ERROR));
                        pipeline.addLast("metricHandler", metricHandler);
                        pipeline.addLast("frameDecoder", new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolDecoder());
                        pipeline.addLast(new OrderProtocolEncoder());
                        pipeline.addLast("flushEnhance", new FlushConsolidationHandler(5, true));
                        pipeline.addLast(businessExecutor, new OrderServerProcessHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
        channelFuture.channel().closeFuture().get();
    }

}
