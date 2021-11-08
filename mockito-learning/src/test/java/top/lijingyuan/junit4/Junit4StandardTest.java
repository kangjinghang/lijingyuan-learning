package top.lijingyuan.junit4;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Junit4StandardTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-11-07
 * @since 1.0.0
 */
public class Junit4StandardTest {

    private static List<String> classLevelList;

    private List<String> testCaseLevelList;

    @BeforeClass
    public static void init() {
        classLevelList = new ArrayList<>();
    }

    @Before
    public void setUp() {
        this.testCaseLevelList = new ArrayList<>();
    }

    @Test
    public void addJavaInTwoList() {
        Assert.assertTrue(classLevelList.add("Java"));
        Assert.assertTrue(testCaseLevelList.add("Java"));
    }

    @Test
    public void addJUnitInTwoList() {
        Assert.assertTrue(classLevelList.add("JUnit"));
        Assert.assertTrue(testCaseLevelList.add("JUnit"));
    }

    @After
    public void tearDown() {
        Assert.assertEquals(1, testCaseLevelList.size());
        testCaseLevelList.clear();
    }

    @AfterClass
    public static void destroy() {
        Assert.assertEquals(2, classLevelList.size());
        classLevelList.clear();
    }

}
