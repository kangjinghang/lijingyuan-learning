package top.lijingyuan.netty.chapter4.client.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import top.lijingyuan.netty.chapter4.common.Operation;
import top.lijingyuan.netty.chapter4.common.RequestMessage;
import top.lijingyuan.netty.chapter4.util.IdUtil;

import java.util.List;

/**
 * OperationToMessageRequestEncoder
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class OperationToMessageRequestEncoder extends MessageToMessageEncoder<Operation> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Operation operation, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), operation);
        out.add(requestMessage);
    }

}
