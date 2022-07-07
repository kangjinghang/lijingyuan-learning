package top.lijingyuan.tool;

/**
 * 位运算应用--奇偶判断
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-12-28
 * @since 1.0.0
 */
public class OperatorDemo9_1 {

    public static void main(String[] args) throws InterruptedException {

        int a = 1;
        // x = 00001100
        int x = 6;
        // y = 00001101
        int y = 7;
        // 和1与运算，结果为0则为偶数
        System.out.println(x & a);
        // 和1与运算，结果为1则为奇数
        System.out.println(y & a);
    }
}
