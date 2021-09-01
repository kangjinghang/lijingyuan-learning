package top.lijingyuan.disruptor.learning;

import lombok.Data;

/**
 * 定义事件event：通过disruptor进行交换的数据类型
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-07-19
 * @since 1.0.0
 */
@Data
public class StringEvent {

    /**
     * 一个字符串的数据
     */
    private String value;

}
