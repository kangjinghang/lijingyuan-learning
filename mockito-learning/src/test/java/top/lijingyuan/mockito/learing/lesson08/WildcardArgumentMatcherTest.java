package top.lijingyuan.mockito.learing.lesson08;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * WildcardArgumentMatcherTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-27
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class WildcardArgumentMatcherTest {

    @Mock
    private SimpleService simpleService;

    @Test
    public void wildcardMethod1() {
        when(simpleService.method1(anyInt(), anyString(), anyCollection(), isA(Serializable.class))).thenReturn(100);
        int result = simpleService.method1(1, "alex", Collections.emptyList(), "Mockito");
        assertThat(result, equalTo(100));

        result = simpleService.method1(2, "wang", Collections.emptySet(), "MockitoForJava");
        assertThat(result, equalTo(100));
    }

    @Test
    public void wildcardMethod1WithSpec() {
        when(simpleService.method1(anyInt(), eq("alex"), anyCollection(), isA(Serializable.class))).thenReturn(100);
        when(simpleService.method1(anyInt(), eq("wang"), anyCollection(), isA(Serializable.class))).thenReturn(200);

        int result = simpleService.method1(1, "alex", Collections.emptyList(), "Mockito");
        assertThat(result, equalTo(100));

        result = simpleService.method1(1, "wang", Collections.emptySet(), "MockitoForJava");
        assertThat(result, equalTo(200));

        result = simpleService.method1(1, "asdb", Collections.emptySet(), "MockitoForJava");
        assertThat(result, equalTo(0));
    }

    @Test
    public void wildcardMethod2() {
        doNothing().when(simpleService).method2(anyInt(), anyString(), anyCollection(), isA(Serializable.class));
        List<Object> emptyList = Collections.emptyList();
        simpleService.method2(1, "alex", emptyList, "Mockito");
        verify(simpleService, times(1)).method2(1, "alex", emptyList, "Mockito");
        // 通配方式，则所有参数都要用通配方式，否则报错
        verify(simpleService, times(1)).method2(anyInt(), eq("alex"), anyCollection(), isA(Serializable.class));
    }

    @After
    public void destroy() {
        reset(simpleService);
    }

}
