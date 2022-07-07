package top.lijingyuan.tool;

/**
 * 位运算应用--两个数交换
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-12-28
 * @since 1.0.0
 */
public class OperatorDemo9_2 {

    public static void main(String[] args) throws InterruptedException {
        // 不用位运算实现两数交换
        int m = 5;
        int n = 10;
        // 原则：一个数对自己异或一次，再异或另一个数，结果为另一个数
        // m = 5        00000101
        // n = 10       00001010
        // m ^ m ^ n
        // 第一步：m ^ m = 00000101 ^ 00000101 = 00000000
        // 第二步：m ^ m ^ n = 00000000 ^ 00001010 = 00001010
        System.out.println(m ^ m ^ n); // 10
        System.out.println(n ^ n ^ m); // 5
        // m ^ m ^ n 和 n ^ n ^ m ，m ^ n = n ^ m
        m = m ^ n;
        n = m ^ n; // 相当于 n = m ^ n ^ n，此时，n = m = 5了
        System.out.println("n = " + n);
        m = m ^ n; // 相当于 m = m ^ n ^ n，此时，n = 原来的 m，所以，相当于 m ^ n ^ 原来的m = 原来的n
        System.out.println("m = " + m);
    }
}
