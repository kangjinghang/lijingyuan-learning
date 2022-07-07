package top.lijingyuan.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * MockitoRunnerTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-07
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoRunnerTest {

    @Mock
    public List<String> list;

    @Test
    public void shouldAddElement2ListCorrect() {
        when(list.add("Java")).thenReturn(true);
        when(list.size()).thenReturn(10);
        assertEquals(10, list.size());
        assertTrue(list.add("Java"));
    }

}
