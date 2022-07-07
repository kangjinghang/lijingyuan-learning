package top.lijingyuan.netty.chapter4.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * OrderFrameDecoder
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-06
 * @since 1.0.0
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {

    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }

}
