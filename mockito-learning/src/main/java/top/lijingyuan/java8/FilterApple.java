package top.lijingyuan.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FilterApple
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-07-05
 * @since 1.0.0
 */
public class FilterApple {

    @FunctionalInterface
    public interface AppleFilter {
        boolean filter(Apple apple);
    }

    public static List<Apple> findApple(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : apples) {
            if (appleFilter.filter(apple)) {
                list.add(apple);
            }
        }
        return list;
    }

    public static class GreenAnd160WeightFilter implements AppleFilter {

        @Override
        public boolean filter(Apple apple) {
            return apple.getColor().equals("green") && apple.getWeight() >= 160;
        }
    }

    public static List<Apple> findGreenApple(List<Apple> apples) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : apples) {
            if ("green".equals(apple.getColor())) {
                list.add(apple);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("green", 150),
                new Apple("yellow", 120), new Apple("green", 170));
//        List<Apple> greenApples = findGreenApple(list);
        List<Apple> greenApples = findApple(list, new GreenAnd160WeightFilter());
//        assert greenApples.size() == 2;

        System.out.println(greenApples);

        findApple(list, apple -> apple.getColor().equals("green"));
    }

}
