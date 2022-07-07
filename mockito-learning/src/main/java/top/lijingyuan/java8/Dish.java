package top.lijingyuan.java8;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Dish
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-07
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
@ToString
public class Dish {

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public enum Type {MEAT, FISH, OTHER}
}


