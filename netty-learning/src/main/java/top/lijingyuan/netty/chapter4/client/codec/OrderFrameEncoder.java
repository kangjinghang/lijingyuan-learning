package top.lijingyuan.netty.chapter4.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * OrderFrameEncoder
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class OrderFrameEncoder extends LengthFieldPrepender {

    public OrderFrameEncoder() {
        super(2);
    }

}
