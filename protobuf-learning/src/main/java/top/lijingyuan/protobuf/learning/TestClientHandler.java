package top.lijingyuan.protobuf.learning;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * TestClientHandler
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-15
 * @since 1.0.0
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage message = null;
        if (0 == randomInt) {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                    .setPerson(MyDataInfo.Person.newBuilder()
                            .setName("张三").setAge(18).setAddress("北京").build()).build();
        } else if (1 == randomInt) {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder()
                            .setName("二哈").setAge(3).build()).build();
        } else {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder()
                            .setName("jerry").setCity("天津").build()).build();
        }
        ctx.channel().writeAndFlush(message);
    }
}
