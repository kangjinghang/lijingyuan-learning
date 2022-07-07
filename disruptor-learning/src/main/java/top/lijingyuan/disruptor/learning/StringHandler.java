package top.lijingyuan.disruptor.learning;

import com.lmax.disruptor.EventHandler;

/**
 * 消费者：定义事件的具体消费的实现
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-19
 * @since 1.0.0
 */
public class StringHandler implements EventHandler<StringEvent> {

    @Override
    public void onEvent(StringEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消费者：" + event.getValue() + "-->sequence=" + sequence + ",endOfBatch=" + endOfBatch);
    }

}
