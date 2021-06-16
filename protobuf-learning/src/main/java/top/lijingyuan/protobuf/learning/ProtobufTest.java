package top.lijingyuan.protobuf.learning;

/**
 * ProtobufTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-15
 * @since 1.0.0
 */
public class ProtobufTest {

    public static void main(String[] args) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder().
                setName("张三").setAge(18).setAddress("北京").build();

        byte[] bytes = student.toByteArray();
        DataInfo.Student student2 = DataInfo.Student.parseFrom(bytes);
        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }

}
