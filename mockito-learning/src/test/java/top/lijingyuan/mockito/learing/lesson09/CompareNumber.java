package top.lijingyuan.mockito.learing.lesson09;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;

/**
 * GreaterThan
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-27
 * @since 1.0.0
 */
public class CompareNumber<T extends Number> extends BaseMatcher<T> {

    private final T value;

    private final Compare<T> compare;

    public CompareNumber(T value, boolean great) {
        this.value = value;
        this.compare = new DefaultNumberCompare<>(great);
    }

    @Override
    public boolean matches(Object actual) {
        return this.compare.compare(value, (T) actual);
    }

    @Factory
    public static <T extends Number> CompareNumber<T> gt(T value) {
        return new CompareNumber<>(value, true);
    }

    @Factory
    public static <T extends Number> CompareNumber<T> lt(T value) {
        return new CompareNumber<>(value, false);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("compare two number failed");
    }

    private interface Compare<T extends Number> {
        boolean compare(T expected, T actual);
    }

    private static class DefaultNumberCompare<T extends Number> implements Compare<T> {

        private final boolean great;

        public DefaultNumberCompare(boolean great) {
            this.great = great;
        }

        @Override
        public boolean compare(T expected, T actual) {
            Class<?> clazz = actual.getClass();
            if (clazz == Integer.class) {
                return great ? (Integer) actual > (Integer) expected : (Integer) actual < (Integer) expected;
            } else if (clazz == Short.class) {
                return great ? (Short) actual > (Short) expected : (Short) actual < (Short) expected;
            } else if (clazz == Byte.class) {
                return great ? (Byte) actual > (Byte) expected : (Byte) actual < (Byte) expected;
            } else if (clazz == Double.class) {
                return great ? (Double) actual > (Double) expected : (Double) actual < (Double) expected;
            } else if (clazz == Float.class) {
                return great ? (Float) actual > (Float) expected : (Float) actual < (Float) expected;
            } else if (clazz == Long.class) {
                return great ? (Long) actual > (Long) expected : (Long) actual < (Long) expected;
            } else {
                throw new AssertionError("The number type " + clazz + "not supported");
            }
        }
    }

}
