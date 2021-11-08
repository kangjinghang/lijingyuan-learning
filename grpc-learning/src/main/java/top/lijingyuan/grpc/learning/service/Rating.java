package top.lijingyuan.grpc.learning.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Rating
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-20
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class Rating {

    private int count;

    private double sum;

    public Rating add(Rating other) {
        return new Rating(this.count + other.count, this.sum + other.sum);
    }

}
