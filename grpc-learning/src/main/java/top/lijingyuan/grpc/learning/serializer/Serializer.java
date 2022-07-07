package top.lijingyuan.grpc.learning.serializer;

import com.google.protobuf.util.JsonFormat;
import top.lijingyuan.grpc.learning.pb.Laptop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Serializer
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
public class Serializer {

    public void writeBinaryFile(Laptop laptop, String filename) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            laptop.writeTo(outputStream);
        }
    }

    public Laptop readBinaryFile(String filename) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            return Laptop.parseFrom(inputStream);
        }
    }

    public void writeJsonFile(Laptop laptop, String filename) throws IOException {
        JsonFormat.Printer printer = JsonFormat.printer()
                .includingDefaultValueFields()
                .preservingProtoFieldNames();

        String jsonString = printer.print(laptop);
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            outputStream.write(jsonString.getBytes());
        }
    }

}
