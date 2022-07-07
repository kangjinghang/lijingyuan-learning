package top.lijingyuan.grpc.learning.serializer;

import org.junit.Assert;
import org.junit.Test;
import top.lijingyuan.grpc.learning.pb.Laptop;
import top.lijingyuan.grpc.learning.sample.Generator;

import java.io.IOException;

/**
 * SerializerTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
public class SerializerTest {

    @Test
    public void writeAndReadBinaryFile() throws IOException {
        String binaryFile = "laptop.bin";

        Laptop laptop1 = new Generator().newLaptop();

        Serializer serializer = new Serializer();

        serializer.writeBinaryFile(laptop1, binaryFile);

        Laptop laptop2 = serializer.readBinaryFile(binaryFile);

        Assert.assertEquals(laptop1, laptop2);

    }

    @Test
    public void writeJsonFile() throws IOException {
        String jsonFile = "laptop.json";

        Laptop laptop = new Generator().newLaptop();

        Serializer serializer = new Serializer();

        serializer.writeJsonFile(laptop, jsonFile);
    }

}
