package top.lijingyuan.protobuf.learning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.time.LocalDateTime;

/**
 * ProtobufTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-15
 * @since 1.0.0
 */
public class ProtobufTest {

    public static void main(String[] args) throws Exception {
//        DataInfo.Student student = DataInfo.Student.newBuilder().
//                setName("张三").setAge(18).setAddress("北京").build();
//
//        byte[] bytes = student.toByteArray();
//        DataInfo.Student student2 = DataInfo.Student.parseFrom(bytes);
//        System.out.println(student2.getName());
//        System.out.println(student2.getAge());
//        System.out.println(student2.getAddress());

        System.out.println(LocalDateTime.now().plusSeconds(1932864).minusDays(60));
        System.out.println(LocalDateTime.now().plusSeconds(1934187).minusDays(60));
        System.out.println(LocalDateTime.now().plusSeconds(1934528).minusDays(60));
        System.out.println(LocalDateTime.now().plusSeconds(1934627).minusDays(60));
        System.out.println(LocalDateTime.now().plusSeconds(1933450).minusDays(60));

        System.out.println("----------------------------------");
        System.out.println(LocalDateTime.now().plusSeconds(2620236).minusDays(60));

        ImmutableList<Character> characters = Lists.charactersOf("abcdefg");

        Lists.partition(characters,10).forEach(System.out::println);
    }

}
