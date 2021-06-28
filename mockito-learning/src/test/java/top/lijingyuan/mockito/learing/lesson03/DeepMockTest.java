package top.lijingyuan.mockito.learing.lesson03;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * DeepMockTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-27
 * @since 1.0.0
 */
public class DeepMockTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    //    @Mock
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Lesson03Service lesson03Service;

//    @Mock
//    private Lesson03 lesson03;

    // stubbing
    @Test
    public void testDeepMock() {
//        Lesson03 lesson03 = lesson03Service.get();
//        lesson03.foo();
//        when(lesson03Service.get()).thenReturn(lesson03);
//        Lesson03 lesson03 = lesson03Service.get();
        // 不会抛出异常，因为 lesson03 是被 mock 出来的，并不会真正地执行，也就是说，这行代码只是占位，不执行
//        lesson03.foo();
        lesson03Service.get().foo();
    }

}
