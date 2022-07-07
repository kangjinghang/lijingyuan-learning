package top.lijingyuan.tool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * TODO
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-12-15
 * @since 1.0.0
 */
public class MyGuest {

    public static void main(String[] args) {
        System.out.println("SZ300203".hashCode() % 256);
        System.out.println(log2(16));
        System.out.println(log2(16 * 1024 * 1024));
        System.out.println(LocalDate.of(2021, 12, 3).atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(LocalDate.of(2021, 12, 4).atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(LocalDate.of(2021, 12, 22).atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(LocalDate.of(2021, 6, 1).atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(LocalDateTime.of(2021, 12, 22, 18, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println("00981".hashCode() % 256);

    }

    static int log2(int val) {
        return Integer.SIZE - 1 - Integer.numberOfLeadingZeros(val);
    }

}
