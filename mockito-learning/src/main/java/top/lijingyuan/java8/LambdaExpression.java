package top.lijingyuan.java8;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * LambdaExpression
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-05
 * @since 1.0.0
 */
public class LambdaExpression {

    Function<String, Integer> flambda = (String s) -> s.length();

    Predicate<Apple> p = (Apple a) -> a.getColor().equals("green");

    Runnable r = () -> {
    };

    Supplier<Apple> s = Apple::new;

    @FunctionalInterface
    public interface Adder {
        int add(int a, int b);
    }

    public interface SmartAdder extends Adder {
        int add(long a, long b);
    }

    @FunctionalInterface
    public interface Empty extends Adder {
    }

    private static List<Apple> filterByWeight(List<Apple> source, LongPredicate predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : source) {
            if (predicate.test(apple.getWeight())) {
                result.add(apple);
            }
        }
        return result;
    }

    private static List<Apple> filterByBiPredicate(List<Apple> source, BiPredicate<String, Long> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : source) {
            if (predicate.test(apple.getColor(), apple.getWeight())) {
                result.add(apple);
            }
        }
        return result;
    }

    private static void simpleTestConsumer(List<Apple> source, Consumer<Apple> consumer) {
        for (Apple apple : source) {
            consumer.accept(apple);
        }
    }

    public static <T> void useConsumer(Consumer<T> consumer, T t) {
        consumer.accept(t);
        consumer.accept(t);
    }

    private static void simpleTestBiConsumer(String c, List<Apple> source, BiConsumer<Apple, String> consumer) {
        for (Apple apple : source) {
            consumer.accept(apple, c);
        }
    }

    public static String testFunction(Apple apple, Function<Apple, String> func) {
        return func.apply(apple);
    }

    public static Apple testBiFunction(String color, long weight, BiFunction<String, Long, Apple> func) {
        return func.apply(color, weight);
    }

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("green", 150),
                new Apple("yellow", 120), new Apple("green", 170));
        List<Apple> result = filterByWeight(list, w -> w > 160);
        System.out.println(result);

        result = filterByBiPredicate(list, (s, w) -> s.equals("green") && w > 160);
        System.out.println(result);

        simpleTestConsumer(list, a -> System.out.println(a.getColor() + "\t" + a.getWeight()));

        simpleTestBiConsumer("xxx", list, (a, s) -> System.out.println(s + "\t" + a.getColor() + "\t" + a.getWeight()));

        System.out.println(testFunction(new Apple("green", 150), a -> a.toString()));

        IntFunction<Double> func = i -> i * 100.0d;
        System.out.println(func.apply(10));

        System.out.println(testBiFunction("red", 100, (s, l) -> new Apple(s, l)));

        Supplier<String> s = String::new;
        System.out.println(s.get().getClass());

        System.out.println(createApple(() -> new Apple("blue", 130)));

        System.out.println("-----------");

        Consumer<String> consumer = str -> System.out.println(str);

        useConsumer(consumer, "echo");

        useConsumer(System.out::println, "echo");

        list.sort((a, b) -> a.getColor().compareTo(b.getColor()));

        System.out.println(list);

        BiFunction<String, Integer, Character> characterBiFunction = String::charAt;
        System.out.println(characterBiFunction.apply("hello", 2));

        String str1 = "Hello";
        Function<Integer, Character> characterFunction = str1::charAt;
        System.out.println(characterFunction.apply(2));

        BiFunction<String, Long, Apple> biFunction = Apple::new;
        Apple red = biFunction.apply("red", 129L);
        System.out.println(red);

        ThreeFunction<String, Long, String, Apple> threeFunction = ComplexApple::new;
        System.out.println(threeFunction.apply("a", 12L, "3"));

        list.sort(Comparator.comparing(Apple::getColor));

        int a = 9;
        IntStream.rangeClosed(1, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .boxed()
                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                .forEach(r -> System.out.println("a=" + r[0] + ",b=" + r[1] + ",c=" + r[2]));

    }

    @AllArgsConstructor
    public static class ComplexApple extends Apple {
        private String category;

        public ComplexApple(String color, long weight, String category) {
            super(color, weight);
            this.category = category;
        }
    }

    private static Apple createApple(Supplier<Apple> supplier) {
        return supplier.get();
    }

    public interface ThreeFunction<T, U, K, R> {
        R apply(T t, U u, K k);
    }

}
