package top.lijingyuan.netty.chapter4.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.lijingyuan.netty.chapter4.common.OperationResult;
import top.lijingyuan.netty.chapter4.common.RequestMessage;
import top.lijingyuan.netty.chapter4.common.ResponseMessage;

/**
 * OrderServerProcessHandler
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
@Slf4j
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        // 内存泄漏示例
//        ByteBuf buffer = ctx.alloc().buffer();
        OperationResult operationResult = requestMessage.getMessageBody().execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);
        // 判断是否可写，防止OOM
        if(ctx.channel().isActive() && ctx.channel().isWritable()){
            ctx.writeAndFlush(responseMessage);
        }else {
            // 要么把数据丢掉，要么存起来，想其他办法再发送
            log.error("message dropped");
        }
    }

}
