package top.lijingyuan.netty.chapter4.client.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.netty.chapter4.common.RequestMessage;
import top.lijingyuan.netty.chapter4.common.keepalive.KeepaliveOperation;
import top.lijingyuan.netty.chapter4.util.IdUtil;

/**
 * KeepAliveHandler
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-08
 * @since 1.0.0
 */
@Slf4j
@ChannelHandler.Sharable
public class KeepAliveHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT) {
            log.info("write idle happen, so need to send keepalive to keep connection not closed by server");
            KeepaliveOperation keepaliveOperation = new KeepaliveOperation();
            RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), keepaliveOperation);
            ctx.writeAndFlush(requestMessage);
        }
        super.userEventTriggered(ctx, evt);
    }

}
