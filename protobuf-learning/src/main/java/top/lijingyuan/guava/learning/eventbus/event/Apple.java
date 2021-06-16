package top.lijingyuan.guava.learning.eventbus.event;

import com.google.common.base.MoreObjects;

/**
 * Apple
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-24
 * @since 1.0.0
 */
public class Apple extends Fruit {

    public Apple(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(this.getName())
                .toString();
    }

}
