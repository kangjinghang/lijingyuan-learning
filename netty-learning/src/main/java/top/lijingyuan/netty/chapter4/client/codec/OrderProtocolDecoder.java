package top.lijingyuan.netty.chapter4.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import top.lijingyuan.netty.chapter4.common.ResponseMessage;

import java.util.List;

/**
 * OrderProtocolDecoder
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        ResponseMessage requestMessage = new ResponseMessage();
        requestMessage.decode(byteBuf);

        out.add(requestMessage);
    }

}
