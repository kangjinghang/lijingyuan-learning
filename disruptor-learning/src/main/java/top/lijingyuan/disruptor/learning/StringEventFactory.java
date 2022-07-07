package top.lijingyuan.disruptor.learning;

import com.lmax.disruptor.EventFactory;

/**
 * 定义事件工厂
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-19
 * @since 1.0.0
 */
public class StringEventFactory implements EventFactory<StringEvent> {

    @Override
    public StringEvent newInstance() {
        return new StringEvent();
    }
}
