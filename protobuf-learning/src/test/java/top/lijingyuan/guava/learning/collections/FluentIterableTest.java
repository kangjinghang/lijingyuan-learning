package top.lijingyuan.guava.learning.collections;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


/**
 * FluentIterableTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class FluentIterableTest {

    private FluentIterable<String> build() {
        ArrayList<String> list = Lists.newArrayList("Alex", "Wang", "Guava", "Scala");
        return FluentIterable.from(list);
    }

    @Test
    public void testFilter() {
        FluentIterable<String> fit = build();
        assertThat(fit.size(), equalTo(4));
        FluentIterable<String> result = fit.filter(e -> e != null && e.length() > 4);
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void testAppend() {
        FluentIterable<String> fit = build();
        ArrayList<String> append = Lists.newArrayList("APPEND");
        assertThat(fit.size(), equalTo(4));
        FluentIterable<String> result = fit.append(append);
        assertThat(result.size(), equalTo(5));
        assertThat(result.contains("APPEND"), equalTo(true));
    }

    @Test
    public void testMatch() {
        FluentIterable<String> fit = build();

        boolean result = fit.allMatch(e -> e != null && e.length() >= 4);
        assertThat(result, equalTo(true));

        result = fit.anyMatch(e -> e != null && e.length() == 5);
        assertThat(result, equalTo(true));

        Optional<String> optional = fit.firstMatch(e -> e != null && e.length() == 5);
        assertThat(optional.isPresent(), equalTo(true));
        assertThat(optional.get(), equalTo("Guava"));
    }

    @Test
    public void testFirst$Last() {
        FluentIterable<String> fit = build();

        Optional<String> optional = fit.first();
        assertThat(optional.isPresent(), equalTo(true));
        assertThat(optional.get(), equalTo("Alex"));

        optional = fit.last();
        assertThat(optional.isPresent(), equalTo(true));
        assertThat(optional.get(), equalTo("Scala"));
    }

    @Test
    public void testLimit() {
        FluentIterable<String> fit = build();

        FluentIterable<String> limit = fit.limit(3);
        assertThat(limit.contains("Scala"), equalTo(false));

        limit = fit.limit(300);
        assertThat(limit.contains("Scala"), equalTo(true));
    }

    @Test
    public void testCopyIn() {
        FluentIterable<String> fit = build();

        ArrayList<String> list = Lists.newArrayList("Java");
        ArrayList<String> result = fit.copyInto(list);

        assertThat(result.size(), equalTo(5));
        assertThat(result.contains("Scala"), equalTo(true));
    }

    @Test
    public void testCycle() {
        FluentIterable<String> fit = build();
        FluentIterable<String> cycle = fit.cycle().limit(20);
        cycle.forEach(System.out::println);
    }

    @Test
    public void testTransform() {
        FluentIterable<String> fit = build();
        fit.transform(e -> e.length()).forEach(System.out::println);
    }

    @Test
    public void testTransformAndConcat() {
        FluentIterable<String> fit = build();
        List<Integer> list = Lists.newArrayList(1);
        fit.transformAndConcat(e -> list).forEach(System.out::println);
    }

    /**
     * A ----- API -------B(Server)
     */
    @Test
    public void testTransformAndConcatInAction() {
        List<Integer> cTypes = Lists.newArrayList(1, 2);
        FluentIterable<Customer> customers = FluentIterable.from(cTypes).transformAndConcat(t -> search(t));
        customers.forEach(System.out::println);
    }

    private List<Customer> search(int type) {
        if (type == 1) {
            return Lists.newArrayList(new Customer(type, "Alex"), new Customer(type, "Tina"));
        } else {
            return Lists.newArrayList(new Customer(type, "Wang"), new Customer(type, "Wen"));
        }
    }

    @Data
    @AllArgsConstructor
    static class Customer {
        private int type;

        private String name;
    }

    @Test
    public void testJoin() {
        FluentIterable<String> fit = build();
        String result = fit.join(Joiner.on(","));
        assertThat(result, equalTo("Alex,Wang,Guava,Scala"));
    }

}
