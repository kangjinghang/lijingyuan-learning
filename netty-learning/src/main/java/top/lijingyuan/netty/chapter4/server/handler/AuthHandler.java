package top.lijingyuan.netty.chapter4.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.netty.chapter4.common.Operation;
import top.lijingyuan.netty.chapter4.common.RequestMessage;
import top.lijingyuan.netty.chapter4.common.auth.AuthOperation;

/**
 * AuthHandler
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-09-08
 * @since 1.0.0
 */
@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        try {
            Operation operation = msg.getMessageBody();
            if(operation instanceof AuthOperation){
                // 去授权
                AuthOperation authOperation = (AuthOperation)operation;
                if(authOperation.execute().isPassAuth()){
                    log.info("pass auth");
                }else {
                    log.error("fail to auth");
                    ctx.close();
                }
            }else {
                log.error("expect fist msg is auth");
                ctx.close();
            }
        } catch (Exception e){
            log.error("exception happen");
            ctx.close();
        }
        finally {
            // 移除 authHandler，不会重复授权
            ctx.pipeline().remove(this);
        }

    }

}
