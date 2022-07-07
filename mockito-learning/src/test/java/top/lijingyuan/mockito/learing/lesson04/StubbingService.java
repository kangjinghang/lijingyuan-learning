package top.lijingyuan.mockito.learing.lesson04;

/**
 * StubbingService
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-27
 * @since 1.0.0
 */
public class StubbingService {

    public int getI() {
        System.out.println("========getI()===========");
        return 10;
    }

    public String getS() {
        System.out.println("========getS()===========");
        throw new RuntimeException();
    }

}
