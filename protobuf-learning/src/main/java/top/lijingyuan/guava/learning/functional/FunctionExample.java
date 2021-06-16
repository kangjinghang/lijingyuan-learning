package top.lijingyuan.guava.learning.functional;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * FunctionExample
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-23
 * @since 1.0.0
 */
public class FunctionExample {

    public static void main(String[] args) throws IOException {
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public @Nullable Integer apply(@Nullable String input) {
                Preconditions.checkNotNull(input, "Yhe input should not by null.");
                return input.length();
            }
        };

        System.out.println(function.apply("hello"));
        System.out.println(Functions.toStringFunction().apply(new ServerSocket(8888)));
        Function<String, Double> composedFunction = Functions.compose(new Function<Integer, Double>() {
            @Override
            public @Nullable Double apply(@Nullable Integer input) {
                return input * 1.0;
            }
        }, new Function<String, Integer>() {
            @Override
            public @Nullable Integer apply(@Nullable String input) {
                return input.length();
            }
        });
        System.out.println(composedFunction.apply("hello"));
    }

}
