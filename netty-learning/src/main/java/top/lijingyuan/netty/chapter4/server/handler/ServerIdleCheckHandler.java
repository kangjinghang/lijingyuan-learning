package top.lijingyuan.netty.chapter4.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * ServerIdleCheckHandler
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-08
 * @since 1.0.0
 */
@Slf4j
public class ServerIdleCheckHandler extends IdleStateHandler {

    public ServerIdleCheckHandler() {
        super(10, 0, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
            log.info("idle check happen, so close the connection");
            ctx.close();
            return;
        }
        super.channelIdle(ctx, evt);
    }

}
