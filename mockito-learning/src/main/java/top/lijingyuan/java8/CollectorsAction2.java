package top.lijingyuan.java8;

import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static top.lijingyuan.java8.CollectorIntroduce.menu;

/**
 * CollectorsAction2
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-07
 * @since 1.0.0
 */
public class CollectorsAction2 {

    public static void main(String[] args) {
        testGroupByConcurrentWithFunction();
        testGroupByConcurrentWithFunctionAndCollector();
        testGroupByConcurrentWithFunctionAndSupplierAndCollector();
        testJoining();
        testJoiningWithDelimiter();
        testJoiningWithDelimiterAndPrefixAndSuffix();
        testMapping();
        testMaxBy();
        testMinBy();
    }

    private static void testGroupByConcurrentWithFunction() {
        System.out.println("testGroupByConcurrent");
        Optional.ofNullable(menu.stream().collect(Collectors.groupingByConcurrent(Dish::getType)))
                .ifPresent(m -> {
                    System.out.println(m.getClass());
                    System.out.println(m);
                });
        System.out.println("------------------");
    }

    private static void testGroupByConcurrentWithFunctionAndCollector() {
        System.out.println("testGroupByConcurrentWithFunctionAndCollector");
        Optional.ofNullable(menu.stream().collect(Collectors.groupingByConcurrent(
                Dish::getType,
                Collectors.averagingInt(Dish::getCalories)
        )))
                .ifPresent(m -> {
                    System.out.println(m.getClass());
                    System.out.println(m);
                });
        System.out.println("------------------");
    }

    private static void testGroupByConcurrentWithFunctionAndSupplierAndCollector() {
        System.out.println("testGroupByConcurrentWithFunctionAndCollector");
        Optional.ofNullable(menu.stream().collect(Collectors.groupingByConcurrent(
                Dish::getType,
                ConcurrentSkipListMap::new,
                Collectors.averagingInt(Dish::getCalories)
        )))
                .ifPresent(m -> {
                    System.out.println(m.getClass());
                    System.out.println(m);
                });
        System.out.println("------------------");
    }

    private static void testJoining() {
        System.out.println("testJoining");
        Optional.ofNullable(menu.stream().map(Dish::getName).collect(Collectors.joining()))
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testJoiningWithDelimiter() {
        System.out.println("testJoiningWithDelimiter");
        Optional.ofNullable(menu.stream().map(Dish::getName).collect(Collectors.joining(",")))
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testJoiningWithDelimiterAndPrefixAndSuffix() {
        System.out.println("testJoiningWithDelimiterAndPrefixAndSuffix");
        Optional.ofNullable(menu.stream().map(Dish::getName).collect(Collectors.joining(",", "Name=[", "]")))
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testMapping() {
        System.out.println("testMapping");

        Optional.ofNullable(menu.stream().collect(
                Collectors.mapping(Dish::getName, Collectors.joining(","))
        ))
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testMaxBy() {
        System.out.println("testMaxBy");

        menu.stream().collect(
                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))
        ).ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testMinBy() {
        System.out.println("testMinBy");

        menu.stream().collect(
                Collectors.minBy(Comparator.comparingInt(Dish::getCalories))
        ).ifPresent(System.out::println);
        System.out.println("------------------");
    }

}
