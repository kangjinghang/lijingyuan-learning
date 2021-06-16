package top.lijingyuan.guava.learning;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * PredictionTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
public class PredictionTest {

    @Test(expected = NullPointerException.class)
    public void testCheckNotNull() {
        checkNotNull(null);
    }

    @Test
    public void testCheckNotNullWithMessage() {
        try {
            checkNotNullWithMessage(null);
        } catch (NullPointerException e) {
            assertThat(e, isA(NullPointerException.class));
            assertThat(e.getMessage(), equalTo("This list should not be empty"));
        }
    }

    @Test
    public void testCheckNotNullWithFormatMessage() {
        try {
            checkNotNullWithFormatMessage(null);
        } catch (NullPointerException e) {
            assertThat(e, isA(NullPointerException.class));
            assertThat(e.getMessage(), equalTo("This list should not be empty and size must be 2"));
        }
    }

    private void checkNotNull(final List<String> list) {
        Preconditions.checkNotNull(list);
    }

    private void checkNotNullWithMessage(final List<String> list) {
        Preconditions.checkNotNull(list, "This list should not be empty");
    }

    private void checkNotNullWithFormatMessage(final List<String> list) {
        Preconditions.checkNotNull(list, "This list should not be empty and size must be %s", 2);
    }

    @Test
    public void testCheckArguments() {
        final String type = "A";
        try {
            Preconditions.checkArgument(type.equals("B"));
        } catch (IllegalArgumentException e) {
            assertThat(e, isA(IllegalArgumentException.class));
        }
    }

    @Test
    public void testCheckState() {
        final String state = "A";
        try {
            Preconditions.checkState(state.equals("B"), "The state is illegal.");
            fail("should not process to here");
        } catch (IllegalStateException e) {
            assertThat(e, isA(IllegalStateException.class));
        }
    }

    @Test
    public void testCheckIndex() {
        try {
            List<String> list = ImmutableList.of();
            Preconditions.checkElementIndex(10, list.size());
        } catch (IndexOutOfBoundsException e) {
            assertThat(e, isA(IndexOutOfBoundsException.class));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testByObjects() {
        Objects.requireNonNull(null);
    }

}
