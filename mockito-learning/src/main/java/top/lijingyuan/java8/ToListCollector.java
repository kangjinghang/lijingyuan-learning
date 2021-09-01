package top.lijingyuan.java8;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * ToListCollector
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-07-07
 * @since 1.0.0
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    private void log(final String log) {
        System.out.println(Thread.currentThread().getName() + "-" + log);
    }

    @Override
    public Supplier<List<T>> supplier() {
        log("supplier");
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        log("accumulator");
        return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        log("combiner");
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        log("finisher");
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        log("characteristics");
        return Collections.unmodifiableSet(EnumSet.of(
                Characteristics.CONCURRENT, Characteristics.IDENTITY_FINISH
        ));
    }

    public static void main(String[] args) {
        ToListCollector<String> collector = new ToListCollector<>();
        String[] arr = new String[]{"kang", "jing", "hang", "java8", "lambda", "string"};
        List<String> result = Arrays.stream(arr).filter(s -> s.length() > 4)
                .collect(collector);
        System.out.println("---------------");
        result = Arrays.stream(arr).parallel().filter(s -> s.length() > 4)
                .collect(collector);
        System.out.println(result);
    }

}
