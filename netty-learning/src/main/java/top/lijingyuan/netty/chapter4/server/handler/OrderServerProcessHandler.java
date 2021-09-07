package top.lijingyuan.netty.chapter4.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.lijingyuan.netty.chapter4.common.OperationResult;
import top.lijingyuan.netty.chapter4.common.RequestMessage;
import top.lijingyuan.netty.chapter4.common.ResponseMessage;

/**
 * OrderServerProcessHandler
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        // 内存泄漏示例
//        ByteBuf buffer = ctx.alloc().buffer();
        OperationResult operationResult = requestMessage.getMessageBody().execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        ctx.writeAndFlush(responseMessage);
    }

}
