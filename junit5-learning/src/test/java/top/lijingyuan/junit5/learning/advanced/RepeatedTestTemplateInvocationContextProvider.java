package top.lijingyuan.junit5.learning.advanced;

import org.junit.jupiter.api.extension.*;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * RepeatedTestTemplateInvocationContextProvider
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-08
 * @since 1.0.0
 */
public class RepeatedTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getRequiredTestMethod().isAnnotationPresent(Repeat.class);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Repeat annotation = context.getRequiredTestMethod().getDeclaredAnnotation(Repeat.class);
        int max = annotation.value();
        return IntStream.rangeClosed(1, max).
                mapToObj(i -> new DefaultRepeatInfo(i, max)).
                map(this::invocationContext);
    }

    private TestTemplateInvocationContext invocationContext(RepeatInfo repeatInfo) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return String.format("[%d-%d]", repeatInfo.current(), repeatInfo.total());
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return Collections.singletonList(
                        new ParameterResolver() {
                            @Override
                            public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
                                return extensionContext.getRequiredTestMethod().isAnnotationPresent(Repeat.class);
                            }

                            @Override
                            public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
                                if (parameterContext.getParameter().getType() == RepeatInfo.class) {
                                    return repeatInfo;
                                }
                                return null;
                            }
                        }
                );
            }
        };
    }

    private static class DefaultRepeatInfo implements RepeatInfo {

        private final int current;

        private final int total;

        public DefaultRepeatInfo(int current, int total) {
            this.current = current;
            this.total = total;
        }

        @Override
        public int current() {
            return this.current;
        }

        @Override
        public int total() {
            return this.total;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", DefaultRepeatInfo.class.getSimpleName() + "[", "]")
                    .add("current=" + current)
                    .add("total=" + total)
                    .toString();
        }
    }

}
